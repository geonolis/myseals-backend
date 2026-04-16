-- ==========================================
-- mySeals Complete Database Schema (PostgreSQL)
-- Optimized for Supabase
-- ==========================================

-- 1. Enable necessary extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. Create ENUM Types
DO $$ BEGIN
    CREATE TYPE seal_status AS ENUM ('REGISTERED', 'IN_STOCK', 'ASSIGNED', 'USED', 'DAMAGED', 'LOST', 'REMOVED');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
    CREATE TYPE movement_type AS ENUM ('TRANSFER_IN', 'TRANSFER_OUT', 'ASSIGN_TO_USER', 'RETURN_FROM_USER', 'REGISTERED_TO_OFFICE');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
    CREATE TYPE audit_action_type AS ENUM ('CREATE', 'READ', 'UPDATE', 'DELETE', 'LOGIN', 'LOGOUT', 'ASSIGN', 'USE', 'TRANSFER');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- 3. Create Core Tables (in order of dependency)

-- roles Table
CREATE TABLE IF NOT EXISTS public.roles (
    role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- offices Table (Supports hierarchy)
CREATE TABLE IF NOT EXISTS public.offices (
    office_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    office_name VARCHAR(255) NOT NULL,
    office_code VARCHAR(50) UNIQUE NOT NULL,
    address TEXT,
    contact_email VARCHAR(255),
    contact_phone VARCHAR(20),
    parent_office_id UUID REFERENCES public.offices(office_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- users Table (Linked to Auth0)
CREATE TABLE IF NOT EXISTS public.users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    auth0_user_id VARCHAR(128) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    full_name VARCHAR(255),
    phone_number VARCHAR(20),
    office_id UUID REFERENCES public.offices(office_id) ON DELETE SET NULL,
    role_id UUID REFERENCES public.roles(role_id) ON DELETE RESTRICT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- seal_batches Table
CREATE TABLE IF NOT EXISTS public.seal_batches (
    batch_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    manufacturer VARCHAR(255) NOT NULL,
    batch_number VARCHAR(100) UNIQUE NOT NULL,
    start_seal_number VARCHAR(50) NOT NULL,
    end_seal_number VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    received_date DATE NOT NULL,
    registered_by UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- seals Table
CREATE TABLE IF NOT EXISTS public.seals (
    seal_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seal_number VARCHAR(50) UNIQUE NOT NULL,
    batch_id UUID REFERENCES public.seal_batches(batch_id) ON DELETE RESTRICT,
    current_status seal_status DEFAULT 'REGISTERED' NOT NULL,
    current_office_id UUID REFERENCES public.offices(office_id) ON DELETE SET NULL,
    assigned_to_user_id UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    registered_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- seal_assignments Table
CREATE TABLE IF NOT EXISTS public.seal_assignments (
    assignment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seal_id UUID REFERENCES public.seals(seal_id) ON DELETE CASCADE UNIQUE NOT NULL,
    assigned_to_user_id UUID REFERENCES public.users(user_id) ON DELETE CASCADE,
    assigned_to_office_id UUID REFERENCES public.offices(office_id) ON DELETE CASCADE,
    assigned_by UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    assignment_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP WITH TIME ZONE,
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_assignment_target CHECK (
        (assigned_to_user_id IS NOT NULL AND assigned_to_office_id IS NULL) OR
        (assigned_to_user_id IS NULL AND assigned_to_office_id IS NOT NULL)
    )
);

-- seal_usage Table
CREATE TABLE IF NOT EXISTS public.seal_usage (
    usage_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seal_id UUID REFERENCES public.seals(seal_id) ON DELETE RESTRICT NOT NULL,
    used_by UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    usage_location TEXT NOT NULL,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    document_reference VARCHAR(255),
    photo_url TEXT,
    notes TEXT,
    used_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- stock_movements Table
CREATE TABLE IF NOT EXISTS public.stock_movements (
    movement_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seal_id UUID REFERENCES public.seals(seal_id) ON DELETE RESTRICT NOT NULL,
    movement_type movement_type NOT NULL,
    from_office_id UUID REFERENCES public.offices(office_id) ON DELETE SET NULL,
    to_office_id UUID REFERENCES public.offices(office_id) ON DELETE SET NULL,
    from_user_id UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    to_user_id UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    moved_by UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    movement_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_movement_source_target CHECK (
        (from_office_id IS NOT NULL OR from_user_id IS NOT NULL) AND
        (to_office_id IS NOT NULL OR to_user_id IS NOT NULL)
    )
);

-- audit_logs Table
CREATE TABLE IF NOT EXISTS public.audit_logs (
    audit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES public.users(user_id) ON DELETE SET NULL,
    action_type audit_action_type NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id UUID,
    old_value JSONB,
    new_value JSONB,
    ip_address INET,
    user_agent TEXT,
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 4. Insert Default Data

-- Insert default roles
INSERT INTO public.roles (role_name, description) VALUES
('HQ_ADMIN', 'Headquarters Administrator with full system access.'),
('OFFICE_MANAGER', 'Manages seals and users within a specific customs office.'),
('EMPLOYEE', 'Field employee who uses and records seal usage.')
ON CONFLICT (role_name) DO NOTHING;

-- 5. Create basic indexes for performance
CREATE INDEX IF NOT EXISTS idx_seals_number ON public.seals(seal_number);
CREATE INDEX IF NOT EXISTS idx_seals_status ON public.seals(current_status);
CREATE INDEX IF NOT EXISTS idx_users_auth0_id ON public.users(auth0_user_id);
CREATE INDEX IF NOT EXISTS idx_audit_logs_timestamp ON public.audit_logs(timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_seal_usage_seal_id ON public.seal_usage(seal_id);

-- ==========================================
-- Schema creation complete.
-- ==========================================

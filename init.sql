DROP TABLE IF EXISTS AUDITORIA CASCADE;
DROP TABLE IF EXISTS USUARIOS CASCADE;
DROP TABLE IF EXISTS SCORE_SEGURIDAD CASCADE;
DROP TABLE IF EXISTS CERTIFICACIONES CASCADE;
DROP TABLE IF EXISTS SALUD_PERSONAS CASCADE;
DROP TABLE IF EXISTS PERSONAS CASCADE;
DROP TABLE IF EXISTS PERMISOS_TRABAJO CASCADE;
DROP TABLE IF EXISTS ROLES CASCADE;
DROP TABLE IF EXISTS RIESGOS CASCADE;
DROP TABLE IF EXISTS DOCUMENTOS CASCADE;
DROP TABLE IF EXISTS MANTENIMIENTOS CASCADE;
DROP TABLE IF EXISTS ACTIVOS CASCADE;
DROP TABLE IF EXISTS DOCUMENTOS_EMPRESA CASCADE;
DROP TABLE IF EXISTS EMPRESAS CASCADE;

CREATE TABLE EMPRESAS (
    empresa_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ruc VARCHAR(20),
    razon_social VARCHAR(255),
    direccion VARCHAR(500),
    sector VARCHAR(100),
    score_seguridad INT
);

CREATE TABLE DOCUMENTOS_EMPRESA (
    documento_empresa_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    nombre_documento VARCHAR(255),
    fecha_vencimiento DATE,
    documento_url VARCHAR(500),
    FOREIGN KEY (empresa_id) REFERENCES EMPRESAS(empresa_id)
);

CREATE TABLE ACTIVOS (
    activo_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    nombre_activo VARCHAR(255),
    tipo_activo VARCHAR(100),
    codigo_activo VARCHAR(100),
    estado VARCHAR(50),
    FOREIGN KEY (empresa_id) REFERENCES EMPRESAS(empresa_id)
);

CREATE TABLE MANTENIMIENTOS (
    mantenimiento_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    activo_id UUID NOT NULL,
    fecha_mantenimiento DATE,
    descripcion TEXT,
    FOREIGN KEY (activo_id) REFERENCES ACTIVOS(activo_id)
);

CREATE TABLE DOCUMENTOS (
    documento_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre_archivo VARCHAR(255),
    tipo_archivo VARCHAR(100),
    version INT,
    fecha_subida TIMESTAMP,
    documento_url VARCHAR(500)
);

CREATE TABLE PERMISOS_TRABAJO (
    permiso_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    solicitado_por_persona_id UUID NOT NULL,
    estado_aprobacion VARCHAR(50),
    fecha_inicio DATE,
    FOREIGN KEY (empresa_id) REFERENCES EMPRESAS(empresa_id)
);

CREATE TABLE RIESGOS (
    riesgo_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre_riesgo VARCHAR(255),
    descripcion TEXT,
    medidas_control TEXT
);

CREATE TABLE ROLES (
    rol_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre_rol VARCHAR(100),
    descripcion TEXT
);

CREATE TABLE USUARIOS (
    usuario_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    persona_id UUID NOT NULL,
    nombre_usuario VARCHAR(100),
    password_hash VARCHAR(255),
    rol_id UUID NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES ROLES(rol_id)
);

CREATE TABLE AUDITORIA (
    auditoria_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL,
    accion VARCHAR(255),
    fecha_hora TIMESTAMP,
    detalles_json JSONB,
    FOREIGN KEY (usuario_id) REFERENCES USUARIOS(usuario_id)
);

CREATE TABLE PERSONAS (
    persona_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    tipo_persona VARCHAR(50) NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE,
    genero VARCHAR(20),
    telefono VARCHAR(20),
    correo VARCHAR(255),
    FOREIGN KEY (empresa_id) REFERENCES EMPRESAS(empresa_id)
);

CREATE TABLE SALUD_PERSONAS (
    salud_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    persona_id UUID NOT NULL,
    grupo_sanguineo VARCHAR(10),
    alergias TEXT,
    historial_medico TEXT,
    FOREIGN KEY (persona_id) REFERENCES PERSONAS(persona_id)
);

CREATE TABLE CERTIFICACIONES (
    certificacion_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    persona_id UUID NOT NULL,
    nombre_certificacion VARCHAR(255) NOT NULL,
    fecha_emision DATE,
    fecha_vencimiento DATE,
    FOREIGN KEY (persona_id) REFERENCES PERSONAS(persona_id)
);

CREATE TABLE SCORE_SEGURIDAD (
    score_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    persona_id UUID NOT NULL,
    puntuacion INT,
    fecha_evaluacion DATE,
    motivo_cambio TEXT,
    FOREIGN KEY (persona_id) REFERENCES PERSONAS(persona_id)
);

ALTER TABLE USUARIOS ADD FOREIGN KEY (persona_id) REFERENCES PERSONAS(persona_id);
ALTER TABLE PERMISOS_TRABAJO ADD FOREIGN KEY (solicitado_por_persona_id) REFERENCES PERSONAS(persona_id);
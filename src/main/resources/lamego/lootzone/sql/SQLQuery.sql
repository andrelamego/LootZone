USE lootzoneDB;

CREATE TABLE t_usuarios (
	Id				INTEGER						IDENTITY(1,1),
	Nome			VARCHAR(50)		NOT NULL,
	Sobrenome		VARCHAR(100)	NOT NULL,
	Email			VARCHAR(100)	NOT NULL	UNIQUE,
	Telefone		CHAR(11)		NOT NULL	UNIQUE CHECK(LEN(Telefone) = 10),
	DataNascimento	DATE			NOT NULL,
	PRIMARY KEY (Id),
)

CREATE TABLE t_compradores (
	UsuarioId		INTEGER,
	Credito			DECIMAL(2,0)	NOT NULL	CHECK(Credito >= 0),
	PRIMARY KEY (UsuarioId),
	FOREIGN KEY (UsuarioId) REFERENCES t_usuarios(Id)
)

CREATE TABLE t_vendedores (
	UsuarioId		INTEGER,
	PRIMARY KEY (UsuarioId),
	FOREIGN KEY (UsuarioId) REFERENCES t_usuarios(Id)
)

CREATE TABLE t_vendedoresPF (
	VendedorId		INTEGER,
	CPF				CHAR(11)		NOT NULL	UNIQUE CHECK(LEN(CPF) = 11),
	PRIMARY KEY (VendedorId),
	FOREIGN KEY (VendedorId) REFERENCES t_vendedores(UsuarioId)
)

CREATE TABLE t_vendedoresPJ (
	VendedorId		INTEGER,
	CNPJ			CHAR(14)		NOT NULL	UNIQUE CHECK(LEN(CNPJ) = 14),
	PRIMARY KEY (VendedorId),
	FOREIGN KEY (VendedorId) REFERENCES t_vendedores(UsuarioId)
)
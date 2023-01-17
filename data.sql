CREATE DATABASE hotel_alura;

CREATE TABLE reserva (
	reservaId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	dataEntrada DATE NOT NULL,
	dataSaida DATE NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	formaPagamento VARCHAR(45) NOT NULL
) AUTO_INCREMENT = 2000;

CREATE TABLE hospede (
	hospedeId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nome VARCHAR(45) NOT NULL,
	sobrenome VARCHAR(45) NOT NULL,
	dataNascimento DATE NOT NULL,
	nacionalidade VARCHAR(45) NOT NULL,
	telefone VARCHAR(15) NOT NULL,
	idReserva INT	
) AUTO_INCREMENT = 1000;

ALTER TABLE hospede 
ADD FOREIGN KEY (idReserva) REFERENCES reserva(reservaId)
ON DELETE CASCADE;

DELIMITER //
CREATE PROCEDURE reserva_hospede (IN data_entrada DATE, IN data_saida DATE, IN valor DECIMAL(10,2), IN forma_pagamento VARCHAR(45),IN nome VARCHAR(45), 
IN sobrenome VARCHAR(45), IN data_nascimento DATE, IN nacionalidade VARCHAR(45), IN telefone VARCHAR(15))
BEGIN
	DECLARE fk INT;
    START TRANSACTION;
	INSERT INTO reserva (dataEntrada, dataSaida, valor, formaPagamento) VALUES(data_entrada, data_saida, valor, forma_pagamento);
	SET fk = LAST_INSERT_ID();
	INSERT INTO hospede(nome,sobrenome,dataNascimento,nacionalidade,telefone,idReserva) VALUES(nome,sobrenome,data_nascimento,nacionalidade,telefone,fk);
    COMMIT;
END //
DELIMITER ;

CALL reserva_hospede('2022-02-17', '2022-06-13', 156.49, 'Cartão de Débito', 'Sophie', 'Marcome', '2022-04-07', 'Greece', '97-525-3416');
CALL reserva_hospede('2022-04-07', '2022-08-09', 183.07, 'Cartão de Crédito', 'Imojean', 'Dibnah', '2022-04-28', 'Russia', '69-438-2491');
CALL reserva_hospede('2022-12-06', '2022-04-24', 403.02, 'Cartão de Crédito', 'Torre', 'Eiler', '2022-10-17', 'China', '53-742-1363');
CALL reserva_hospede('2022-11-10', '2022-10-14', 298.3, 'Cartão de Crédito', 'Chevy', 'Kingzeth', '2022-12-31', 'Bolivia', '11-275-8529');
CALL reserva_hospede('2022-04-19', '2022-01-27', 951.57, 'Dinheiro', 'Alonso', 'Cully', '2020-06-28', 'Honduras', '21-606-6423');
CALL reserva_hospede('2022-01-19', '2022-07-15', 659.98, 'Dinheiro', 'Wilone', 'Britland', '2020-12-29', 'Portugal', '07-630-1343');
CALL reserva_hospede('2022-06-16', '2022-12-07', 322.98, 'Cartão de Crédito', 'Marlo', 'Feehan', '2020-11-13', 'United States', '28-483-8259');
CALL reserva_hospede('2022-07-26', '2022-08-16', 102.39, 'Dinheiro', 'Brett', 'MacMaster', '2021-07-22', 'Sri Lanka', '15-602-9420');
CALL reserva_hospede('2022-04-12', '2022-04-16', 749.71, 'Cartão de Crédito', 'Betti', 'Helian', '2022-08-30', 'China', '94-651-0222');
CALL reserva_hospede('2022-09-30', '2022-01-23', 457.42, 'Dinheiro', 'Siana', 'Sidwell', '2022-12-09', 'China', '81-937-4516');

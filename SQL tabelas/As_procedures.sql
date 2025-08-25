--------------------------------------------------------
--  Arquivo criado - sábado-junho-28-2025   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Procedure PR_CADASTRAR_CLIENTE_COMPLETO
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PAULO"."PR_CADASTRAR_CLIENTE_COMPLETO" (
    p_estado            IN VARCHAR2,
    p_cidade            IN VARCHAR2,
    p_bairro            IN VARCHAR2,
    p_cep               IN VARCHAR2,
    p_tipo_endereco     IN VARCHAR2,
    p_log               IN VARCHAR2,
    p_ds_log            IN VARCHAR2,
    p_nr_log            IN NUMBER,
    p_complemento       IN VARCHAR2,
    p_tipo_telefone     IN VARCHAR2,
    p_ddd               IN NUMBER,
    p_prefixo_sufixo    IN VARCHAR2,
    p_email             IN VARCHAR2,
    p_nome_razao        IN VARCHAR2,
    p_cnpj              IN VARCHAR2,
    p_limite_credito    IN NUMBER
)
AS
    v_cod_estado        NUMBER;
    v_cod_cidade        NUMBER;
    v_cod_bairro        NUMBER;
    v_cod_cep           NUMBER;
    v_cod_tipo_end      NUMBER;
    v_cod_endereco      NUMBER;
    v_cod_tipo_tel      NUMBER;
    v_cod_telefone      NUMBER;
    v_cod_email         NUMBER;
    v_cod_pessoa        NUMBER;
    v_cod_pj            NUMBER;
    v_cod_cliente       NUMBER;
BEGIN
    -- ESTADO
    BEGIN
        SELECT COD_ESTADO INTO v_cod_estado
        FROM Estado WHERE UPPER(TRIM(ESTADO)) = UPPER(TRIM(p_estado));
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            SELECT PAULO.SEQ_COD_ESTADO.NEXTVAL INTO v_cod_estado FROM dual;
            INSERT INTO Estado (COD_ESTADO, ESTADO) VALUES (v_cod_estado, TRIM(p_estado));
    END;

    -- CIDADE
    BEGIN
        SELECT COD_CIDADE INTO v_cod_cidade
        FROM Cidade 
        WHERE UPPER(TRIM(CIDADE)) = UPPER(TRIM(p_cidade)) AND FK_COD_ESTADO = v_cod_estado;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            SELECT PAULO.SEQ_COD_CIDADE.NEXTVAL INTO v_cod_cidade FROM dual;
            INSERT INTO Cidade (COD_CIDADE, CIDADE, FK_COD_ESTADO) VALUES (v_cod_cidade, TRIM(p_cidade), v_cod_estado);
    END;

    -- BAIRRO
    BEGIN
        SELECT COD_BAIRRO INTO v_cod_bairro
        FROM Bairro 
        WHERE UPPER(TRIM(BAIRRO)) = UPPER(TRIM(p_bairro)) AND FK_COD_CIDADE = v_cod_cidade;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            SELECT PAULO.SEQ_COD_BAIRRO.NEXTVAL INTO v_cod_bairro FROM dual;
            INSERT INTO Bairro (COD_BAIRRO, BAIRRO, FK_COD_CIDADE) VALUES (v_cod_bairro, TRIM(p_bairro), v_cod_cidade);
    END;

    -- CEP
    BEGIN
        SELECT COD_CEP INTO v_cod_cep
        FROM Cep 
        WHERE TRIM(CEP) = TRIM(p_cep) AND FK_COD_BAIRRO = v_cod_bairro;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            SELECT PAULO.SEQ_CEP.NEXTVAL INTO v_cod_cep FROM dual;
            INSERT INTO Cep (COD_CEP, CEP, FK_COD_BAIRRO) VALUES (v_cod_cep, TRIM(p_cep), v_cod_bairro);
    END;

    -- TIPO ENDEREÇO
    BEGIN
        SELECT COD_TIPO_END INTO v_cod_tipo_end
        FROM Tipo_endereco WHERE UPPER(TRIM(DS_END)) = UPPER(TRIM(p_tipo_endereco));
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            SELECT PAULO.SEQ_TIPO_ENDERECO.NEXTVAL INTO v_cod_tipo_end FROM dual;
            INSERT INTO Tipo_endereco (COD_TIPO_END, DS_END) VALUES (v_cod_tipo_end, TRIM(p_tipo_endereco));
    END;

    -- ENDEREÇO
    SELECT PAULO.SEQ_ENDERECO.NEXTVAL INTO v_cod_endereco FROM dual;
    INSERT INTO Endereco (
        COD_ENDERECO, LOG, DS_LOG, NR_LOG, COMPLEMENTO, FK_COD_CEP, FK_COD_TIPO_END
    ) VALUES (
        v_cod_endereco, TRIM(p_log), TRIM(p_ds_log), p_nr_log, TRIM(p_complemento), v_cod_cep, v_cod_tipo_end
    );

    -- TIPO TELEFONE
    BEGIN
        SELECT COD_TIPO_TEL INTO v_cod_tipo_tel
        FROM Tipo_telefone WHERE UPPER(TRIM(DS_TEL)) = UPPER(TRIM(p_tipo_telefone));
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            SELECT PAULO.SEQ_TIPO_TEL.NEXTVAL INTO v_cod_tipo_tel FROM dual;
            INSERT INTO Tipo_telefone (COD_TIPO_TEL, DS_TEL) VALUES (v_cod_tipo_tel, TRIM(p_tipo_telefone));
    END;

    -- TELEFONE
    SELECT PAULO.SEQ_TELEFONE.NEXTVAL INTO v_cod_telefone FROM dual;
    INSERT INTO Telefone (
        COD_TEL, DDD, PREFIXO_SUFIXO, FK_COD_TIPO_TEL
    ) VALUES (
        v_cod_telefone, p_ddd, TRIM(p_prefixo_sufixo), v_cod_tipo_tel
    );

    -- EMAIL
    SELECT PAULO.SEQ_EMAIL.NEXTVAL INTO v_cod_email FROM dual;
    INSERT INTO Email (COD_EMAIL, EMAIL) VALUES (v_cod_email, TRIM(p_email));

    -- PESSOA JURÍDICA
    BEGIN
        -- Se o CNPJ já existir, traz o COD_PJ e COD_PESSOA correspondente
        SELECT COD_PJ, FK_COD_PESSOA INTO v_cod_pj, v_cod_pessoa
        FROM Pessoa_Juridica
        WHERE TRIM(CNPJ) = TRIM(p_cnpj);
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Criar nova pessoa
            SELECT PAULO.SEQ_PESSOA.NEXTVAL INTO v_cod_pessoa FROM dual;
            INSERT INTO Pessoa (COD_PESSOA, NOME_RAZAO_SOCIAL) VALUES (v_cod_pessoa, TRIM(p_nome_razao));

            -- Criar nova pessoa jurídica
            SELECT PAULO.SEQ_PJ.NEXTVAL INTO v_cod_pj FROM dual;
            INSERT INTO Pessoa_Juridica (COD_PJ, CNPJ, FK_COD_PESSOA)
            VALUES (v_cod_pj, TRIM(p_cnpj), v_cod_pessoa);

            -- ASSOCIAÇÕES
            INSERT INTO Pessoa_Endereco (FK_COD_PESSOA, FK_COD_ENDERECO)
            VALUES (v_cod_pessoa, v_cod_endereco);

            INSERT INTO Pessoa_Telefone (FK_COD_PESSOA, FK_COD_TELEFONE)
            VALUES (v_cod_pessoa, v_cod_telefone);

            INSERT INTO Pessoa_Email (FK_COD_PESSOA, FK_COD_EMAIL)
            VALUES (v_cod_pessoa, v_cod_email);
    END;

    -- CLIENTE
    BEGIN
        SELECT COD_CLIENTE INTO v_cod_cliente
        FROM Cliente WHERE FK_COD_PJ = v_cod_pj;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            SELECT PAULO.SEQ_CLIENTE.NEXTVAL INTO v_cod_cliente FROM dual;
            INSERT INTO Cliente (COD_CLIENTE, LIMITE_CREDITO, FK_COD_PJ)
            VALUES (v_cod_cliente, p_limite_credito, v_cod_pj);
    END;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;

/
--------------------------------------------------------
--  DDL for Procedure PR_CADASTRAR_FORNECEDOR
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PAULO"."PR_CADASTRAR_FORNECEDOR" (
    p_nome IN VARCHAR2,
    p_cnpj IN CHAR
) AS
    v_cod_pessoa NUMBER;
    v_cod_pj NUMBER;
    v_cod_fornecedor NUMBER;
BEGIN
    -- Tenta obter cod_pj da pessoa_juridica pelo cnpj
    BEGIN
        SELECT cod_pj INTO v_cod_pj
        FROM pessoa_juridica
        WHERE cnpj = p_cnpj;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Não existe, insere Pessoa e Pessoa_Juridica
            v_cod_pessoa := SEQ_PESSOA.NEXTVAL;
            INSERT INTO Pessoa (COD_PESSOA, NOME_RAZAO_SOCIAL)
            VALUES (v_cod_pessoa, p_nome);

            v_cod_pj := SEQ_PJ.NEXTVAL;
            INSERT INTO Pessoa_Juridica (COD_PJ, CNPJ, FK_COD_PESSOA)
            VALUES (v_cod_pj, p_cnpj, v_cod_pessoa);
    END;

    -- Agora verifica se fornecedor já existe para esse cod_pj
    BEGIN
        SELECT cod_fornecedor INTO v_cod_fornecedor
        FROM fornecedor
        WHERE fk_cod_pj = v_cod_pj;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Não existe fornecedor, insere
            v_cod_fornecedor := SEQ_FORNECEDOR.NEXTVAL;
            INSERT INTO Fornecedor (COD_FORNECEDOR, FK_COD_PJ)
            VALUES (v_cod_fornecedor, v_cod_pj);
    END;

    COMMIT;
END;

/
--------------------------------------------------------
--  DDL for Procedure PR_CADASTRAR_MATERIA_PRIMA_COM_TIPO
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PAULO"."PR_CADASTRAR_MATERIA_PRIMA_COM_TIPO" (
    p_cod_tipo_medida IN NUMBER,
    p_ds_medida IN VARCHAR2,
    p_ds_materia IN VARCHAR2,
    p_qtd_necessaria IN NUMBER
) IS
    v_count_tipo NUMBER;
    v_cod_materia_prima NUMBER;
BEGIN
    -- Verificar se o Tipo_De_Medida já existe
    SELECT COUNT(*) INTO v_count_tipo
    FROM Tipo_De_Medida
    WHERE COD_TIPO_MEDIDA = p_cod_tipo_medida;

    IF v_count_tipo = 0 THEN
        -- Inserir novo tipo de medida
        INSERT INTO Tipo_De_Medida (COD_TIPO_MEDIDA, DS_MEDIDA)
        VALUES (p_cod_tipo_medida, p_ds_medida);
    END IF;

    -- Gerar código para Materia_Prima (simples auto increment via seq ou max+1)
    SELECT NVL(MAX(COD_MATERIA_PRIMA), 0) + 1 INTO v_cod_materia_prima FROM Materia_Prima;

    -- Inserir Matéria-Prima vinculada ao Tipo_De_Medida
    INSERT INTO Materia_Prima (COD_MATERIA_PRIMA, DS_MATERIA, QTD_NECESSARIA, FK_COD_TIPO_MEDIDA)
    VALUES (v_cod_materia_prima, p_ds_materia, p_qtd_necessaria, p_cod_tipo_medida);

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Cadastro concluído: Tipo De Medida e Matéria Prima.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;

/
--------------------------------------------------------
--  DDL for Procedure PR_CADASTRAR_MATERIA_TIPO
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PAULO"."PR_CADASTRAR_MATERIA_TIPO" (
    P_DS_MATERIA IN VARCHAR2,
    P_QTD_NECESSARIA IN NUMBER,
    P_DS_MEDIDA IN VARCHAR2
) AS
    V_COD_TIPO_MEDIDA NUMBER;
BEGIN
    -- Tenta buscar o tipo de medida já existente
    BEGIN
        SELECT COD_TIPO_MEDIDA INTO V_COD_TIPO_MEDIDA
        FROM TIPO_DE_MEDIDA
        WHERE DS_MEDIDA = P_DS_MEDIDA;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Se não existir, insere e pega o novo código
            V_COD_TIPO_MEDIDA := SEQ_TIPO_MEDIDA.NEXTVAL;
            INSERT INTO TIPO_DE_MEDIDA (COD_TIPO_MEDIDA, DS_MEDIDA)
            VALUES (V_COD_TIPO_MEDIDA, P_DS_MEDIDA);
    END;

    -- Insere na tabela MATERIA_PRIMA com o código da medida
    INSERT INTO MATERIA_PRIMA (
        COD_MATERIA_PRIMA,
        DS_MATERIA,
        QTD_NECESSARIA,
        FK_COD_TIPO_MEDIDA
    ) VALUES (
        SEQ_MATERIA_PRIMA.NEXTVAL,
        P_DS_MATERIA,
        P_QTD_NECESSARIA,
        V_COD_TIPO_MEDIDA
    );
END;

/
--------------------------------------------------------
--  DDL for Procedure PR_CADASTRAR_PAGAMENTO
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PAULO"."PR_CADASTRAR_PAGAMENTO" (
    p_dt_pagamento IN DATE,
    p_dt_vencimento IN DATE,
    p_valor_total IN NUMBER,
    p_fk_cod_pedido IN NUMBER,
    p_fk_cod_forma_pgto IN NUMBER,
    p_fk_cod_tipo_pgto IN NUMBER,
    v_cod_pagamento OUT NUMBER
) AS
BEGIN
    -- Inserção na tabela PAGAMENTO com código gerado pela sequência
    INSERT INTO PAGAMENTO (
        COD_PAGAMENTO, DT_PAGAMENTO, DT_VENCIMENTO, VALOR_TOTAL,
        FK_COD_PEDIDO, FK_COD_FORMA_PGTO
    ) VALUES (
        SEQ_PAGAMENTO.NEXTVAL, p_dt_pagamento, p_dt_vencimento, p_valor_total,
        p_fk_cod_pedido, p_fk_cod_forma_pgto
    )
    RETURNING COD_PAGAMENTO INTO v_cod_pagamento;

    -- Inserção na tabela associativa entre pagamento e tipo_pagamento
    INSERT INTO PAGAMENTO_TIPO_PAGAMENTO (
        FK_COD_PAGAMENTO, FK_COD_TIPO_PAGAMENTO
    ) VALUES (
        v_cod_pagamento, p_fk_cod_tipo_pgto
    );

    COMMIT;
END;

/
--------------------------------------------------------
--  DDL for Procedure PR_CADASTRAR_VENDEDOR_PF
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PAULO"."PR_CADASTRAR_VENDEDOR_PF" (
    p_nome IN VARCHAR2,
    p_cpf IN CHAR,
    p_sexo IN CHAR,
    p_dt_nasc IN DATE,
    p_cargo IN VARCHAR2,
    p_salario IN NUMBER,
    p_departamento IN VARCHAR2,
    p_matricula IN VARCHAR2,
    p_comissao IN NUMBER
) AS
    v_cod_pessoa NUMBER;
    v_cod_pf NUMBER;
    v_cod_func NUMBER;
    v_cod_vend NUMBER;
BEGIN
    -- Inserir Pessoa e pegar o código
    v_cod_pessoa := SEQ_PESSOA.NEXTVAL;
    INSERT INTO Pessoa (COD_PESSOA, NOME_RAZAO_SOCIAL)
    VALUES (v_cod_pessoa, p_nome);

    -- Inserir Pessoa_Fisica e pegar o código
    v_cod_pf := SEQ_PF.NEXTVAL;
    INSERT INTO Pessoa_Fisica (COD_PF, CPF, SEXO, DT_NASC, ESTADO_CIVIL, FK_COD_PESSOA)
    VALUES (v_cod_pf, p_cpf, p_sexo, p_dt_nasc, 'Solteiro', v_cod_pessoa);

    -- Inserir Funcionario e pegar o código
    v_cod_func := SEQ_FUNCIONARIO.NEXTVAL;
    INSERT INTO Funcionario (COD_FUNCIONARIO, FK_COD_PF, CARGO, SALARIO, DEPARTAMENTO)
    VALUES (v_cod_func, v_cod_pf, p_cargo, p_salario, p_departamento);

    -- Inserir Vendedor e pegar o código
    v_cod_vend := SEQ_VENDEDOR.NEXTVAL;
    INSERT INTO Vendedor (COD_VENDEDOR, FK_COD_PF, FK_COD_PJ, MATRICULA, COMISSAO)
    VALUES (v_cod_vend, v_cod_pf, NULL, p_matricula, p_comissao);

    COMMIT;
END;

/
--------------------------------------------------------
--  DDL for Procedure TRANSPORTADORA_ZONA
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "PAULO"."TRANSPORTADORA_ZONA" (
    p_cod_transportadora IN NUMBER,
    p_cod_zona IN NUMBER
) AS
BEGIN
    INSERT INTO Transportadora_Zona_Entrega (
        FK_COD_TRANSPORTADORA,
        FK_COD_ZN_ENTREGA
    ) VALUES (
        p_cod_transportadora,
        p_cod_zona
    );

    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Esta região já está registrada para essa transportadora.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;

/

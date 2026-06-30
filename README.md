
# SIGATORED - Sistema Integrado de Gestão de Ativos e Ocorrências na Rede Elétrica de Distribuição

Este projeto é uma aplicação de terminal desenvolvida em **Java** para a disciplina de LP2. 
O sistema simula o controle e despacho dinâmico de Ordens de Serviço (OS) para equipes de campo em uma concessionária de rede elétrica, validando regras físicas de ativos e regras operacionais de segurança.

## Equipe (Grupo X)
* **[Antonio Gabriel]** 
* **[Guilherme Matias]** -

## Como executar via terminal:

Para rodar o sistema na sua máquina, siga os passos abaixo:

### Pré-requisitos
* Ter o **JDK** instalado.

### Passo a Passo
1. Faça o clone do repositório ou baixe os arquivos fonte para o seu computador.
2. Abra o terminal e navegue até a pasta raiz onde os arquivos `.java` estão localizados:
   ```
   cd caminho/para/a/pasta/do/projeto
```
```

3. Compile todos os arquivos Java utilizando o compilador `javac`:
```
javac *.java

```


4. Inicie a aplicação executando a classe principal `Main`:
```bash
java Main

```

## Principais Funcionalidades (CRUD e Lógica)

* **Gestão de Equipes (CRUD):** Cadastro, listagem, atualização de status de disponibilidade e remoção de equipes de campo.
* **Despacho Inteligente de OS:** O sistema gerencia uma fila dinâmica (`Queue`). Se não houver equipe disponível e capacitada, a OS aguarda na fila e é automaticamente puxada assim que uma equipe compatível é liberada.
* **Persistência de Dados:** As informações das equipes cadastradas são salvas automaticamente em um arquivo `equipes.txt` ao sair do sistema, e recarregadas na inicialização.

---

## Conceitos de POO Aplicados

O sistema foi desenhado visando a aplicação rigorosa de boas práticas de Engenharia de Software e POO:

* **Encapsulamento:** Proteção de todos os atributos nas classes de domínio, acessados estritamente via métodos públicos (Getters/Setters) e métodos de ação.
* **Herança:** Hierarquias claras criadas para Ativos de Rede (`Poste`, `Transformador`) e Serviços (`LigacaoNova`, `Desligamento`, `ReparoEmergencial`).
* **Polimorfismo:** Aplicação de métodos polimórficos nas classes filhas de `Servico` para avaliar dinamicamente prazos e a obrigatoriedade de certificação de "Linha Viva" (NR-10).
* **Tratamento de Exceções:** Implementação das exceções personalizadas `CapacidadeExcedidaException` e `EquipeIndisponivelException` para blindar o sistema contra violações de regras de negócio físicas e de segurança.
* **Estado Dinâmico:** A classe `OrdemServico` possui uma máquina de estados rígida (ABERTA -> DESPACHADA -> EM_EXECUCAO -> CONCLUIDA) que impede transições indevidas.

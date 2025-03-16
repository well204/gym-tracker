# Arquitetura e Modelagem do Clone do Hevy

## 1. Visão Geral do Sistema

O clone do Hevy será um aplicativo para rastreamento de treinos de academia com as seguintes funcionalidades principais:

- Registro de treinos (exercícios, séries, repetições, peso)
- Criação e gerenciamento de rotinas de treino
- Acompanhamento de progresso
- Perfil de usuário
- Compartilhamento social
- Sistema de amizades/seguidores

## 2. Arquitetura do Sistema

### Arquitetura de Alto Nível

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Frontend Web   │     │  Mobile App     │     │  Admin Panel    │
│  (Angular)      │◄───►│  (React Native) │◄───►│  (Angular)      │
└─────────────────┘     └─────────────────┘     └─────────────────┘
          ▲                      ▲                      ▲
          │                      │                      │
          ▼                      ▼                      ▼
┌───────────────────────────────────────────────────────────────┐
│                        API Gateway                            │
└───────────────────────────────────────────────────────────────┘
          ▲                      ▲                      ▲
          │                      │                      │
          ▼                      ▼                      ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│ Microserviço    │     │ Microserviço    │     │ Microserviço    │
│ de Usuários     │     │ de Treinos      │     │ Social          │
└─────────────────┘     └─────────────────┘     └─────────────────┘
          ▲                      ▲                      ▲
          │                      │                      │
          ▼                      ▼                      ▼
┌───────────────────────────────────────────────────────────────┐
│                  Banco de Dados (PostgreSQL)                  │
└───────────────────────────────────────────────────────────────┘
```

### Componentes do Backend (Spring)

1. **API Gateway**: Gerencia autenticação, roteamento e logging.
2. **Microserviço de Usuários**: Gerencia perfis, autenticação e autorização.
3. **Microserviço de Treinos**: Gerencia exercícios, treinos e rotinas.
4. **Microserviço Social**: Gerencia amizades, compartilhamento e feed.

### Frontend (Angular)

1. **Módulo de Autenticação**: Login, registro e recuperação de senha.
2. **Módulo de Perfil**: Visualização e edição de perfil.
3. **Módulo de Treinos**: Registro e visualização de treinos.
4. **Módulo de Rotinas**: Criação e edição de rotinas.
5. **Módulo Social**: Amigos, compartilhamento e feed.
6. **Módulo de Análise**: Gráficos e estatísticas de progresso.

### Mobile (React Native)

Mesmos módulos do frontend web, adaptados para interface mobile.

## 3. Modelagem de Dados (Entidades e Relacionamentos)

### Diagrama de Entidades

```
┌─────────────┐     ┌───────────────┐     ┌───────────────┐
│   User      │     │   Workout     │     │   Exercise    │
├─────────────┤     ├───────────────┤     ├───────────────┤
│ id          │1   *│ id            │1   *│ id            │
│ username    ├────►│ userId        │     │ name          │
│ email       │     │ name          ├────►│ category      │
│ password    │     │ date          │     │ equipment     │
│ profile_pic │     │ notes         │     │ instructions  │
│ height      │     │ duration      │     │ muscleGroups  │
│ weight      │     │ isCompleted   │     └───────────┬───┘
│ bodyFat     │     └───────┬───────┘                 │
└─────────────┘             │                         │
      ▲                     │                         │
      │                     ▼                         │
┌─────┴─────┐      ┌───────────────┐                  │
│ Friendship│      │ WorkoutSet    │                  │
├───────────┤      ├───────────────┤                  │
│ id        │      │ id            │                  │
│ userId    │      │ workoutId     │                  │
│ friendId  │      │ exerciseId    │◄─────────────────┘
│ status    │      │ order         │
└───────────┘      │ reps          │
                   │ weight        │
                   │ duration      │
                   │ restTime      │
                   │ isCompleted   │
                   └───────────────┘
                           ▲
                           │
┌───────────────┐     ┌───┴───────────┐     ┌───────────────┐
│   Routine     │     │ RoutineDay    │     │ RoutineExercise│
├───────────────┤     ├───────────────┤     ├───────────────┤
│ id            │1   *│ id            │1   *│ id            │
│ userId        ├────►│ routineId     ├────►│ routineDayId  │
│ name          │     │ name          │     │ exerciseId    │
│ description   │     │ dayNumber     │     │ order         │
│ isPublic      │     └───────────────┘     │ sets          │
└───────────────┘                           │ reps          │
                                           │ weight        │
                                           └───────────────┘
```

### Descrição das Entidades

#### User
- **id**: Identificador único do usuário (UUID)
- **username**: Nome de usuário (único)
- **email**: Email do usuário (único)
- **password**: Senha criptografada
- **profilePic**: URL da foto de perfil
- **height**: Altura em cm
- **weight**: Peso em kg
- **bodyFat**: Percentual de gordura corporal
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

#### Exercise
- **id**: Identificador único do exercício
- **name**: Nome do exercício
- **category**: Categoria (ex: Peito, Costas, Pernas)
- **equipment**: Equipamento necessário
- **instructions**: Instruções de execução
- **muscleGroups**: Grupos musculares trabalhados (array)
- **isCustom**: Indica se é um exercício personalizado
- **userId**: ID do usuário (nulo se for padrão do sistema)
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

#### Workout
- **id**: Identificador único do treino
- **userId**: ID do usuário
- **name**: Nome do treino
- **date**: Data do treino
- **notes**: Anotações sobre o treino
- **duration**: Duração em minutos
- **isCompleted**: Status de conclusão
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

#### WorkoutSet
- **id**: Identificador único da série
- **workoutId**: ID do treino
- **exerciseId**: ID do exercício
- **order**: Ordem da série no treino
- **reps**: Número de repetições
- **weight**: Peso utilizado
- **duration**: Duração (para exercícios baseados em tempo)
- **restTime**: Tempo de descanso após a série
- **isCompleted**: Status de conclusão
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

#### Routine
- **id**: Identificador único da rotina
- **userId**: ID do usuário
- **name**: Nome da rotina
- **description**: Descrição da rotina
- **isPublic**: Visibilidade da rotina
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

#### RoutineDay
- **id**: Identificador único do dia da rotina
- **routineId**: ID da rotina
- **name**: Nome do dia (ex: "Dia de Peito", "Pernas")
- **dayNumber**: Número do dia na semana (1-7)
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

#### RoutineExercise
- **id**: Identificador único do exercício na rotina
- **routineDayId**: ID do dia da rotina
- **exerciseId**: ID do exercício
- **order**: Ordem do exercício no dia
- **sets**: Número de séries
- **reps**: Número de repetições
- **weight**: Peso sugerido
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

#### Friendship
- **id**: Identificador único da amizade
- **userId**: ID do usuário
- **friendId**: ID do amigo
- **status**: Status (pendente, aceito, rejeitado)
- **createdAt**: Data de criação
- **updatedAt**: Data de atualização

## 4. Regras de Negócio

### Autenticação e Autorização
1. Usuários devem se autenticar para acessar o sistema.
2. Usuários só podem modificar seus próprios dados e treinos.
3. Rotinas públicas podem ser visualizadas por qualquer usuário.
4. Perfis de usuário podem ser públicos ou privados.

### Treinos
1. Um treino pode conter múltiplos exercícios.
2. Cada exercício em um treino pode ter múltiplas séries.
3. Usuários podem marcar treinos como concluídos.
4. Usuários podem adicionar anotações aos treinos.
5. Sistema deve calcular automaticamente a duração total do treino.

### Rotinas
1. Um usuário pode criar múltiplas rotinas.
2. Uma rotina pode ter múltiplos dias.
3. Um dia de rotina pode ter múltiplos exercícios.
4. Usuários podem copiar rotinas públicas para seu perfil.
5. Usuários podem converter um treino em rotina.

### Progresso
1. Sistema deve rastrear o progresso em peso, repetições e volume total.
2. Sistema deve gerar gráficos de progresso por exercício.
3. Sistema deve calcular recordes pessoais (PR) automaticamente.

### Social
1. Usuários podem seguir outros usuários.
2. Relacionamentos de amizade requerem aceitação do destinatário.
3. Usuários podem compartilhar treinos concluídos no feed.
4. Usuários podem curtir e comentar treinos compartilhados.

## 5. APIs Principais

### API de Usuários (Spring)
- `POST /api/users/register`: Registrar novo usuário
- `POST /api/users/login`: Autenticar usuário
- `GET /api/users/profile`: Obter perfil do usuário logado
- `PUT /api/users/profile`: Atualizar perfil do usuário
- `GET /api/users/{id}`: Obter perfil público de um usuário
- `GET /api/users/search?q={query}`: Pesquisar usuários

### API de Exercícios (Spring)
- `GET /api/exercises`: Listar exercícios padrão
- `POST /api/exercises`: Criar exercício personalizado
- `GET /api/exercises/{id}`: Obter detalhes de um exercício
- `PUT /api/exercises/{id}`: Atualizar exercício personalizado
- `DELETE /api/exercises/{id}`: Excluir exercício personalizado

### API de Treinos (Spring)
- `GET /api/workouts`: Listar treinos do usuário
- `POST /api/workouts`: Criar novo treino
- `GET /api/workouts/{id}`: Obter detalhes de um treino
- `PUT /api/workouts/{id}`: Atualizar treino
- `DELETE /api/workouts/{id}`: Excluir treino
- `GET /api/workouts/history?exerciseId={id}`: Obter histórico de um exercício

### API de Rotinas (Spring)
- `GET /api/routines`: Listar rotinas do usuário
- `POST /api/routines`: Criar nova rotina
- `GET /api/routines/{id}`: Obter detalhes de uma rotina
- `PUT /api/routines/{id}`: Atualizar rotina
- `DELETE /api/routines/{id}`: Excluir rotina
- `GET /api/routines/public`: Listar rotinas públicas
- `POST /api/routines/{id}/copy`: Copiar rotina pública

### API Social (Spring)
- `GET /api/social/friends`: Listar amigos
- `POST /api/social/friends/request/{userId}`: Enviar solicitação de amizade
- `PUT /api/social/friends/accept/{requestId}`: Aceitar solicitação
- `PUT /api/social/friends/reject/{requestId}`: Rejeitar solicitação
- `GET /api/social/feed`: Obter feed de atividades
- `POST /api/social/share/{workoutId}`: Compartilhar treino
- `POST /api/social/posts/{postId}/like`: Curtir post
- `POST /api/social/posts/{postId}/comment`: Comentar post

## 6. Considerações de Segurança

### Autenticação
- Implementar JWT (JSON Web Tokens) para autenticação
- Armazenar senhas com bcrypt ou algoritmo similar
- Implementar limite de tentativas de login (rate limiting)

### Autorização
- Implementar RBAC (Role-Based Access Control)
- Verificar permissões em cada endpoint da API
- Validar propriedade dos recursos (userId) antes de operações

### Proteção de Dados
- Validar entrada de dados em todos os endpoints
- Implementar HTTPS para todas as comunicações
- Sanitizar dados antes de armazenar no banco
- Implementar CORS de forma adequada

## 7. Recomendações para Implementação

### Backend (Spring)
1. Utilizar Spring Boot para configuração rápida
2. Implementar Spring Security para autenticação/autorização
3. Utilizar Spring Data JPA para persistência
4. Documentar APIs com Swagger/OpenAPI
5. Implementar testes unitários e de integração
6. Utilizar Docker para containerização

### Frontend Web (Angular)
1. Implementar estrutura modular (core, shared, features)
2. Utilizar Angular Material ou PrimeNG para UI
3. Implementar guardas de rota para proteção
4. Utilizar NgRx para gerenciamento de estado
5. Implementar lazy loading para módulos

### Mobile (React Native)
1. Utilizar Redux ou Context API para gerenciamento de estado
2. Implementar navigation stack com React Navigation
3. Utilizar componentes nativos quando necessário
4. Otimizar desempenho com memoização
5. Implementar armazenamento offline com AsyncStorage

## 8. Princípios de Engenharia de Software

Além da elicitação de requisitos, é fundamental aplicar os seguintes princípios de engenharia de software ao projeto:

### Princípios SOLID
1. **S - Single Responsibility Principle (Princípio da Responsabilidade Única)**
   - Cada classe deve ter apenas uma razão para mudar
   - Ex: Separar a classe `UserService` de `AuthenticationService`

2. **O - Open/Closed Principle (Princípio Aberto/Fechado)**
   - Entidades devem estar abertas para extensão, mas fechadas para modificação
   - Ex: Usar interfaces e classes abstratas para permitir extensibilidade

3. **L - Liskov Substitution Principle (Princípio da Substituição de Liskov)**
   - Subtipos devem ser substituíveis por seus tipos base
   - Ex: Todas as implementações de `ExerciseRepository` devem funcionar onde a interface é esperada

4. **I - Interface Segregation Principle (Princípio da Segregação de Interfaces)**
   - Clientes não devem ser forçados a depender de interfaces que não utilizam
   - Ex: Separar `WorkoutService` de `WorkoutStatisticsService`

5. **D - Dependency Inversion Principle (Princípio da Inversão de Dependência)**
   - Depender de abstrações, não de implementações concretas
   - Ex: Injetar interfaces como `UserRepository` em vez de implementações específicas

### Outros Princípios Essenciais
1. **DRY (Don't Repeat Yourself)**
   - Evitar duplicação de código e conhecimento
   - Implementar camadas de abstração e utilitários reutilizáveis

2. **KISS (Keep It Simple, Stupid)**
   - Manter o código simples e compreensível
   - Evitar otimizações prematuras e complexidade desnecessária

3. **YAGNI (You Aren't Gonna Need It)**
   - Não adicionar funcionalidades até que sejam realmente necessárias
   - Implementar o MVP (Minimum Viable Product) primeiro

4. **Padrões de Design**
   - **Repository Pattern**: Para acesso a dados
   - **Factory Pattern**: Para criação de objetos complexos
   - **Strategy Pattern**: Para algoritmos intercambiáveis
   - **Observer Pattern**: Para notificações e eventos

### Qualidade de Código
1. **Clean Code**
   - Nomes significativos para variáveis, métodos e classes
   - Métodos pequenos e focados
   - Comentários apenas quando necessário
   - Formatação consistente

2. **Code Reviews**
   - Revisão sistemática de código
   - Uso de ferramentas de análise estática (SonarQube, ESLint)

### Testes
1. **TDD (Test-Driven Development)**
   - Escrever testes antes do código de produção
   - Ciclo: Red (falha) → Green (sucesso) → Refactor

2. **Testes Unitários**
   - Testar componentes isoladamente
   - Usar mocks para dependências externas

3. **Testes de Integração**
   - Testar interações entre componentes
   - Verificar fluxos completos

4. **Testes End-to-End**
   - Testar a aplicação do ponto de vista do usuário
   - Automação de UI com Selenium, Cypress, etc.

### DevOps e CI/CD
1. **Controle de Versão**
   - Gitflow ou Trunk-based development
   - Commits semânticos e atomizados
   - Branches para features, releases e hotfixes

2. **Integração Contínua**
   - Builds automáticos em cada commit
   - Execução automática de testes

3. **Entrega Contínua**
   - Automatizar o deployment
   - Ambientes de desenvolvimento, teste e produção

### Documentação
1. **Documentação de Código**
   - Javadoc/JSDoc para APIs
   - README para projetos e módulos

2. **Documentação da Arquitetura**
   - Diagramas de arquitetura (C4 Model)
   - Decisões de design documentadas (ADRs)

3. **Documentação de API**
   - Swagger/OpenAPI para documentação de endpoints
   - Exemplos de uso e casos de teste

### Gestão de Projeto
1. **Metodologias Ágeis**
   - Scrum ou Kanban para gerenciamento de tarefas
   - Sprints de 1-2 semanas
   - Retrospectivas regulares

2. **Histórias de Usuário**
   - Formato: "Como [papel], quero [ação] para [benefício]"
   - Critérios de aceitação claros

3. **Priorização**
   - MoSCoW (Must, Should, Could, Won't)
   - Valor de negócio vs. esforço técnico

## 9. Estrutura do Banco de Dados

### Tabelas Principais

```sql
-- Users
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    profile_pic VARCHAR(255),
    height FLOAT,
    weight FLOAT,
    body_fat FLOAT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Exercises
CREATE TABLE exercises (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    equipment VARCHAR(100),
    instructions TEXT,
    muscle_groups JSONB,
    is_custom BOOLEAN DEFAULT FALSE,
    user_id UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Workouts
CREATE TABLE workouts (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) NOT NULL,
    name VARCHAR(100) NOT NULL,
    date TIMESTAMP NOT NULL,
    notes TEXT,
    duration INTEGER,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Workout Sets
CREATE TABLE workout_sets (
    id UUID PRIMARY KEY,
    workout_id UUID REFERENCES workouts(id) NOT NULL,
    exercise_id UUID REFERENCES exercises(id) NOT NULL,
    "order" INTEGER NOT NULL,
    reps INTEGER,
    weight FLOAT,
    duration INTEGER,
    rest_time INTEGER,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Routines
CREATE TABLE routines (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_public BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Routine Days
CREATE TABLE routine_days (
    id UUID PRIMARY KEY,
    routine_id UUID REFERENCES routines(id) NOT NULL,
    name VARCHAR(100) NOT NULL,
    day_number INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Routine Exercises
CREATE TABLE routine_exercises (
    id UUID PRIMARY KEY,
    routine_day_id UUID REFERENCES routine_days(id) NOT NULL,
    exercise_id UUID REFERENCES exercises(id) NOT NULL,
    "order" INTEGER NOT NULL,
    sets INTEGER NOT NULL,
    reps INTEGER,
    weight FLOAT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Friendships
CREATE TABLE friendships (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) NOT NULL,
    friend_id UUID REFERENCES users(id) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, friend_id)
);

-- Social Posts
CREATE TABLE social_posts (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) NOT NULL,
    workout_id UUID REFERENCES workouts(id),
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Post Likes
CREATE TABLE post_likes (
    id UUID PRIMARY KEY,
    post_id UUID REFERENCES social_posts(id) NOT NULL,
    user_id UUID REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(post_id, user_id)
);

-- Post Comments
CREATE TABLE post_comments (
    id UUID PRIMARY KEY,
    post_id UUID REFERENCES social_posts(id) NOT NULL,
    user_id UUID REFERENCES users(id) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

# Architecture Documentation

## Overview

This microservice follows **Clean Architecture** (also known as **Hexagonal Architecture** or **Ports and Adapters**) principles to ensure maintainability, testability, and independence from frameworks and external systems.

## Architectural Principles

### 1. Dependency Rule

Dependencies flow **inward only**:

```
Infrastructure → Application → Domain
```

- **Domain**: No dependencies on other layers (pure business logic)
- **Application**: Depends only on Domain
- **Infrastructure**: Depends on Application and Domain

### 2. Separation of Concerns

Each layer has distinct responsibilities:

- **Domain**: Business entities, rules, and abstractions
- **Application**: Use cases and orchestration logic
- **Infrastructure**: Technical implementation details

### 3. Testability

Business logic can be tested without frameworks, databases, or external services.

---

## Layer Structure

### Domain Layer (`domain/`)

The **core** of the application containing pure business logic.

```
domain/
├── exception/           # Business exceptions
├── model/              # Business entities (aggregates, value objects)
│   ├── common/
│   ├── position/
│   └── ride/
├── repository/         # Repository interfaces (abstractions)
│   ├── position/
│   └── ride/
└── service/           # Domain services (business logic that doesn't belong to entities)
    └── distance/
```

**Characteristics**:
- No framework dependencies
- No annotations (except Lombok for boilerplate reduction)
- Contains only business logic
- Defines interfaces for repositories (implemented by infrastructure)

**Examples**:
- `Ride.java` - Core business entity
- `RideRepository.java` - Repository abstraction
- `DistanceCalculator.java` - Domain service

---

### Application Layer (`application/`)

The **use case** layer that orchestrates business operations.

```
application/
├── mapper/                    # Domain ↔ Use Case DTO mapping
│   ├── position/
│   └── ride/
├── output/                    # Output ports (interfaces for external systems)
│   ├── i18n/gateway/         # Internationalization abstraction
│   ├── queue/gateway/        # Message queue abstraction
│   └── rest/                 # External REST API abstractions
│       ├── dto/              # Domain-oriented DTOs
│       │   └── account/
│       └── gateway/          # Gateway interfaces (output ports)
│           ├── account/
│           └── payment/
└── usecase/
    ├── dto/                  # Use case input/output DTOs
    │   ├── acceptride/
    │   ├── completeride/
    │   ├── registerposition/
    │   ├── requestride/
    │   ├── searchride/
    │   └── startride/
    └── feature/              # Use case implementations (by feature)
        ├── acceptride/
        │   ├── AcceptRideUseCase.java      (interface)
        │   └── AcceptRideUseCaseImpl.java  (implementation)
        ├── completeride/
        ├── registerposition/
        ├── requestride/
        ├── searchride/
        └── startride/
```

**Characteristics**:
- Defines **output ports** (gateway interfaces)
- Contains use case logic (orchestration)
- Uses DTOs to communicate with infrastructure layer
- Framework-agnostic (except Spring for dependency injection)

**Key Concepts**:

#### Output Ports (Gateways)
Interfaces defined in the application layer, implemented by infrastructure:

```java
// application/output/rest/gateway/account/SearchAccountGateway.java
public interface SearchAccountGateway {
    AccountDTO search(Id id);
}
```

#### Use Cases
Business operations exposed to the infrastructure layer:

```java
// application/usecase/feature/requestride/RequestRideUseCase.java
public interface RequestRideUseCase extends UseCase<RequestRideInputDTO, RequestRideOutputDTO> {
    RequestRideOutputDTO execute(RequestRideInputDTO inputDTO);
}
```

#### DTOs in Application Layer

**Use Case DTOs** (`application/usecase/dto/`):
- Transfer data between infrastructure and use cases
- Feature-oriented (e.g., `RequestRideInputDTO`, `CompleteRideOutputDTO`)

**Gateway DTOs** (`application/output/rest/dto/`):
- Domain-oriented representations for external systems
- Abstract away external API details
- Example: `AccountDTO` (business concept) vs `SearchAccountRS` (API contract)

---

### Infrastructure Layer (`infrastructure/`)

The **implementation** layer containing all technical details.

```
infrastructure/
├── config/                    # Spring configuration
├── input/                     # Input adapters (controllers, listeners)
│   └── rest/
│       ├── controller/       # REST controllers (by feature)
│       │   ├── acceptride/
│       │   ├── completeride/
│       │   ├── registerposition/
│       │   ├── requestride/
│       │   ├── searchride/
│       │   └── startride/
│       ├── dto/              # REST API contracts (by feature)
│       │   ├── acceptride/
│       │   ├── completeride/
│       │   ├── exception/
│       │   ├── registerposition/
│       │   ├── requestride/
│       │   └── searchride/
│       ├── exception/        # REST exception handling
│       └── mapper/           # REST ↔ Use Case DTO mapping
│           ├── position/
│           └── ride/
└── output/                   # Output adapters (repositories, clients)
    ├── database/
    │   ├── entity/          # JPA entities
    │   │   ├── position/
    │   │   └── ride/
    │   ├── mapper/          # Entity ↔ Domain mapping
    │   │   ├── position/
    │   │   └── ride/
    │   └── repository/      # Repository implementations
    │       ├── position/
    │       └── ride/
    ├── i18n/                # I18n gateway implementation
    ├── queue/               # Message queue implementation
    │   ├── dto/
    │   └── gateway/
    └── rest/                # External REST API integrations
        ├── client/          # Feign clients (*RestClient)
        ├── dto/             # External API contracts (by service)
        │   ├── account/
        │   └── payment/
        ├── gateway/         # Gateway implementations (adapters)
        │   ├── account/
        │   └── payment/
        └── mapper/          # External API ↔ Domain mapping
            ├── account/
            └── payment/
```

**Characteristics**:
- Contains all framework-specific code (Spring, JPA, Feign, etc.)
- Implements interfaces defined in application layer
- Manages external system communication

**Key Concepts**:

#### Input Adapters
Receive requests from external sources (HTTP, messages, etc.):

```java
// infrastructure/input/rest/controller/requestride/RequestRideController.java
@RestController
public class RequestRideController {
    private final RequestRideUseCase useCase;

    @PostMapping("/v1/rides")
    public RequestRideRS requestRide(@RequestBody RequestRideRQ request) {
        // Convert REST DTO → Use Case DTO
        // Execute use case
        // Convert Use Case DTO → REST DTO
    }
}
```

#### Output Adapters
Implement application layer interfaces (output ports):

```java
// infrastructure/output/rest/gateway/account/SearchAccountGatewayImpl.java
@Component
public class SearchAccountGatewayImpl implements SearchAccountGateway {
    private final SearchAccountRestClient client;

    @Override
    public AccountDTO search(Id id) {
        SearchAccountRS response = client.execute(id.getValue());
        return mapper.toAccountDTO(response);
    }
}
```

#### REST Clients
Communicate with external services:

```java
// infrastructure/output/rest/client/SearchAccountRestClient.java
@FeignClient(name = "searchAccountClient", url = "${integration.account.host}")
public interface SearchAccountRestClient {
    @GetMapping(value = "/v1/accounts/{id}")
    SearchAccountRS execute(@PathVariable String id);
}
```

---

## Package Organization Rationale

### Feature-Based vs Service-Based Organization

**Input Layer** (organized by **feature**):
```
infrastructure/input/rest/dto/
├── acceptride/      # Business feature
├── completeride/    # Business feature
├── requestride/     # Business feature
└── searchride/      # Business feature
```

**Rationale**: Input DTOs represent business operations from the client's perspective.

**Output Layer** (organized by **external service**):
```
infrastructure/output/rest/dto/
├── account/         # External service
└── payment/         # External service
```

**Rationale**: Output DTOs represent contracts with specific external systems.

### Why DTOs Exist in Multiple Layers

#### Application Layer DTOs
- **Purpose**: Business-oriented representations
- **Example**: `AccountDTO` contains business concepts
- **Stability**: Changes only when business requirements change

#### Infrastructure Layer DTOs
- **Purpose**: External API contracts
- **Example**: `SearchAccountRS` matches external API structure
- **Stability**: Changes when external API changes

**Benefit**: Isolates your business logic from external API changes.

---

## Data Flow

### Request Flow (Input)

```
1. REST Request (RequestRideRQ)
   ↓
2. Controller receives request
   ↓
3. Mapper converts RequestRideRQ → RequestRideInputDTO
   ↓
4. Use Case executes business logic
   ↓
5. Mapper converts RequestRideOutputDTO → RequestRideRS
   ↓
6. REST Response (RequestRideRS)
```

### External Integration Flow (Output)

```
1. Use Case needs external data
   ↓
2. Calls Gateway interface (SearchAccountGateway)
   ↓
3. Gateway implementation (SearchAccountGatewayImpl) executes
   ↓
4. Calls REST Client (SearchAccountRestClient)
   ↓
5. Receives external DTO (SearchAccountRS)
   ↓
6. Mapper converts SearchAccountRS → AccountDTO
   ↓
7. Returns domain-oriented AccountDTO to Use Case
```

---

## Naming Conventions

### Suffixes

| Suffix | Layer | Purpose | Example |
|--------|-------|---------|---------|
| `UseCase` | Application | Use case interface | `RequestRideUseCase` |
| `UseCaseImpl` | Application | Use case implementation | `RequestRideUseCaseImpl` |
| `Gateway` | Application | Output port interface | `SearchAccountGateway` |
| `GatewayImpl` | Infrastructure | Output adapter implementation | `SearchAccountGatewayImpl` |
| `RestClient` | Infrastructure | Feign client interface | `SearchAccountRestClient` |
| `Repository` | Domain | Repository interface | `RideRepository` |
| `RepositoryImpl` | Infrastructure | Repository implementation | `RideRepositoryImpl` |
| `JpaRepository` | Infrastructure | Spring Data interface | `RideJpaRepository` |
| `Mapper` | All layers | Object mapping | `RideRestMapper` |
| `Entity` | Infrastructure | JPA entity | `RideEntity` |
| `DTO` | Application | Domain-oriented DTO | `AccountDTO` |
| `RQ` | Infrastructure | Request DTO | `RequestRideRQ` |
| `RS` | Infrastructure | Response DTO | `RequestRideRS` |

### Package Naming

- **By feature**: When organizing business operations (`acceptride/`, `completeride/`)
- **By aggregate**: When organizing domain models (`ride/`, `position/`)
- **By external service**: When organizing external integrations (`account/`, `payment/`)

---

## Design Patterns Used

### 1. Hexagonal Architecture (Ports and Adapters)
- **Ports**: Interfaces defined in application layer (e.g., `SearchAccountGateway`)
- **Adapters**: Implementations in infrastructure layer (e.g., `SearchAccountGatewayImpl`)

### 2. Use Case Pattern
Each business operation is encapsulated in a use case:
```java
public interface UseCase<I extends UseCaseInputDTO, O extends UseCaseOutputDTO> {
    O execute(I inputDTO);
}
```

### 3. Repository Pattern
Data access abstracted through repository interfaces:
```java
// Domain defines the contract
public interface RideRepository {
    Ride save(Ride ride);
    Optional<Ride> findById(Id id);
}

// Infrastructure provides the implementation
@Component
public class RideRepositoryImpl implements RideRepository {
    private final RideJpaRepository jpaRepository;
    // ...
}
```

### 4. Gateway Pattern
External service communication abstracted through gateways:
```java
// Application defines what it needs
public interface PaymentGateway {
    void processPayment(PaymentRequest request);
}

// Infrastructure provides how to get it
@Component
public class PaymentGatewayImpl implements PaymentGateway {
    private final PaymentRestClient client;
    // ...
}
```

### 5. Mapper Pattern
Object transformation isolated in dedicated mappers:
- `RideRestMapper`: REST DTO ↔ Use Case DTO
- `RideDatabaseMapper`: JPA Entity ↔ Domain Model
- `AccountGatewayMapper`: External API DTO ↔ Domain DTO

---

## Testing Strategy

### Domain Layer Tests
- Pure unit tests
- No mocking required (pure business logic)
- Test entities, value objects, and domain services

### Application Layer Tests
- Unit tests with mocked gateways and repositories
- Focus on use case orchestration logic
- Test business workflows

### Infrastructure Layer Tests
- Integration tests with real frameworks
- Test controllers with MockMvc
- Test repositories with test databases
- Test external integrations with WireMock/contract tests

---

## Benefits of This Architecture

### 1. **Independence**
- Business logic independent of frameworks
- Can swap databases without changing domain
- Can replace REST with GraphQL without changing use cases

### 2. **Testability**
- Domain and application layers easily unit-tested
- Can test business logic without infrastructure

### 3. **Maintainability**
- Clear separation of concerns
- Easy to locate and modify code
- Changes isolated to specific layers

### 4. **Scalability**
- Feature-based organization supports microservice decomposition
- Can extract features into separate services

### 5. **Team Collaboration**
- Clear boundaries enable parallel development
- Teams can work on different layers simultaneously

---

## Common Questions

### Q: Why have both `AccountDTO` and `SearchAccountRS`?

**A**:
- `AccountDTO` (application layer): Represents what **your business needs** from an account
- `SearchAccountRS` (infrastructure layer): Represents what the **external API provides**

If the external API changes, you only update the infrastructure layer. Your business logic remains unchanged.

### Q: Why are use case interfaces and implementations in the same package?

**A**: Since there's typically only one implementation per use case, keeping them together reduces unnecessary package depth and improves navigability. The interface still defines the contract for testing and dependency injection.

### Q: When should I create a domain service vs a use case?

**A**:
- **Domain Service**: Business logic that doesn't naturally belong to a single entity (e.g., `DistanceCalculator`)
- **Use Case**: Orchestrates a specific business operation (e.g., `RequestRideUseCase`)

### Q: Why is the queue directory structure empty?

**A**: The structure is prepared for future message queue integration. When implementing asynchronous messaging, follow the same pattern as REST integrations:
- Define gateway interfaces in `application/output/queue/gateway/`
- Implement in `infrastructure/output/queue/gateway/`

---

## References

- [Clean Architecture (Robert C. Martin)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture (Alistair Cockburn)](https://alistair.cockburn.us/hexagonal-architecture/)
- [Domain-Driven Design (Eric Evans)](https://www.domainlanguage.com/ddd/)

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | 2025-11-29 | Initial architecture documentation |
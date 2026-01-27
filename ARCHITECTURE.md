# Arquitetura do Bettr - Guia Completo

## 📋 Índice
1. [Visão Geral](#visão-geral)
2. [Stack Tecnológica](#stack-tecnológica)
3. [Estrutura de Camadas](#estrutura-de-camadas)
4. [Padr��es e Convenções](#padrões-e-convenções)
   - [Camada de Dados](#1-camada-de-dados-data-layer)
   - [Camada de Domínio](#2-camada-de-domínio-domain-layer)
   - [Camada de Apresentação](#3-camada-de-apresentação-presentation-layer)
   - [Design System](#4-design-system)
   - [Dependency Injection](#5-dependency-injection-koin)
   - [Navegação](#6-navegação)
   - [Cache e Gerenciamento de Estado](#7-cache-e-gerenciamento-de-estado)
5. [Guia Prático: Como Adicionar Novas Features](#guia-prático-como-adicionar-novas-features)
6. [Exemplos Completos](#exemplos-completos)
7. [Referências de Código](#referências-de-código)

---

## 🎯 Visão Geral

O Bettr é um aplicativo **Kotlin Multiplatform (KMP)** que compartilha código entre Android e iOS. A arquitetura segue os princípios de **Clean Architecture** combinado com **MVVM (Model-View-ViewModel)**, garantindo separação de responsabilidades, testabilidade e manutenibilidade.

### Princípios Fundamentais
- **Separação de Camadas**: Cada camada tem uma responsabilidade específica
- **Injeção de Dependências**: Usando Koin para gerenciar dependências
- **Fluxo Unidirecional de Dados**: Estados fluem do ViewModel para a UI
- **Type Safety**: Uso extensivo de tipos para evitar erros em tempo de execução

---

## 🛠 Stack Tecnológica

### Core
- **Kotlin Multiplatform**: Código compartilhado entre Android e iOS
- **Compose Multiplatform**: UI declarativa para ambas as plataformas
- **Coroutines**: Programação assíncrona

### Networking
- **Ktor Client**: Cliente HTTP multiplataforma
- **Kotlinx Serialization**: Serialização/deserialização de JSON

### Dependency Injection
- **Koin**: Framework de injeção de dependências leve e fácil

### Navigation
- **Compose Navigation**: Navegação declarativa

### State Management
- **StateFlow**: Gerenciamento de estado reativo

---

## 🏗 Estrutura de Camadas

```
composeApp/src/commonMain/kotlin/org/example/bettr/
│
├── data/                      # Camada de Dados
│   ├── network/               # Configuração e cliente HTTP
│   │   ├── api/               # Interfaces e implementações de APIs
│   │   ├── dto/               # DTOs (Data Transfer Objects)
│   │   ├── util/              # Utilitários de rede (Result, NetworkError)
│   │   ├── BettrClient.kt     # Wrapper para chamadas seguras de API
│   │   └── HttpClientFactory.kt
│   └── repository/            # Repositórios (intermediário entre API e Domain)
│
├── domain/                    # Camada de Dom��nio (Regras de Negócio)
│   ├── model/                 # Modelos de domínio (entidades de negócio)
│   └── usecase/               # Casos de uso (lógica de negócio)
│
├── presentation/              # Camada de Apresentação (UI)
│   ├── [feature]/             # Organizado por feature
│   │   ├── view/              # Telas Composable
│   │   ├── viewmodel/         # ViewModels
│   │   ├── state/             # Estados da UI
│   │   ├── action/            # Ações da UI
│   │   ├���─ effect/            # Efeitos colaterais (navegação, toasts)
│   │   ├── model/             # Modelos específicos da UI
│   │   └── mapper/            # Mappers (Domain → UI)
│
├── designsystem/              # Sistema de Design
│   ├── components/            # Componentes reutilizáveis
│   ├── theme/                 # Cores, tipografia, temas
│   └── util/                  # Utilitários de UI
│
├── di/                        # Dependency Injection
│   ├── AppModule.kt           # ViewModels e UseCases
│   ├── NetworkModule.kt       # Networking
│   └── KoinInit.kt            # Inicialização do Koin
│
├── navigation/                # Navegação
│   ├── Route.kt               # Definição de rotas
│   └── BettrNavHost.kt        # Configuração do NavHost
│
└── App.kt                     # Ponto de entrada do app
```

---

## 📐 Padrões e Convenções

### 1. Camada de Dados (Data Layer)

#### DTOs (Data Transfer Objects)
**Localização**: `data/network/dto/`

DTOs representam os dados que vêm da API (JSON). Use `@Serializable` do Kotlinx Serialization.

```kotlin
@Serializable
data class DreamTypeDto(
    @SerialName("key") val key: String,
    @SerialName("label") val label: String,
    @SerialName("emoji") val emoji: String
)
```

**Convenções:**
- Sempre adicione `@Serializable`
- Use `@SerialName` para mapear nomes de campos JSON diferentes
- Sufixo: `Dto`
- Mantenha apenas campos que vêm da API (sem lógica)

#### API Interfaces
**Localização**: `data/network/api/`

```kotlin
interface OnboardingApi {
    suspend fun getDreamTypes(): Result<List<DreamTypeDto>, NetworkError>
}
```

**Convenções:**
- Interface define o contrato
- Métodos são `suspend fun`
- Retornam `Result<T, NetworkError>`

#### API Implementation
**Localização**: `data/network/api/`

```kotlin
class OnboardingApiImpl(
    private val httpClient: HttpClient,
    private val bettrClient: BettrClient
) : OnboardingApi {
    private companion object {
        const val BASE_URL = "https://bettr-production.up.railway.app"
    }

    override suspend fun getDreamTypes(): Result<List<DreamTypeDto>, NetworkError> {
        return bettrClient.safeApiCall {
            httpClient.get("$BASE_URL/dreams-types").body()
        }
    }
}
```

**Convenções:**
- Use `BettrClient.safeApiCall {}` para tratamento automático de erros
- Injete `HttpClient` e `BettrClient` via construtor
- BASE_URL em `companion object`

#### BettrClient - Safe API Call Wrapper
**Localização**: `data/network/BettrClient.kt`

Este é o componente central para tratamento de erros de rede. Ele captura todas as exceções possíveis do Ktor e as converte em `NetworkError` tipado.

```kotlin
class BettrClient {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Result<T, NetworkError> {
        val response = try {
            apiCall()
        } catch (e: ConnectTimeoutException) {
            return Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: SocketTimeoutException) {
            return Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: ClientRequestException) {
            return when (e.response.status.value) {
                401 -> Result.Error(NetworkError.UNAUTHORIZED)
                408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                409 -> Result.Error(NetworkError.CONFLICT)
                413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
                429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
                else -> Result.Error(NetworkError.UNKNOWN)
            }
        } catch (e: ServerResponseException) {
            return Result.Error(NetworkError.SERVER_ERROR)
        } catch (e: Exception) {
            return Result.Error(NetworkError.NO_INTERNET)
        }

        return Result.Success(response)
    }
}
```

**Como usar:**
```kotlin
bettrClient.safeApiCall {
    httpClient.get("$BASE_URL/endpoint").body()
}
```

#### Result Type
**Localização**: `data/network/util/Result.kt`

O tipo `Result` é um sealed interface que representa sucesso ou erro de forma type-safe:

```kotlin
sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E : Error>(val error: E): Result<Nothing, E>
}
```

**Funções úteis:**
- `map()`: Transforma o valor de sucesso
- `onSuccess()`: Executa ação se sucesso
- `onError()`: Executa ação se erro

#### NetworkError Enum
**Localização**: `data/network/util/NetworkError.kt`

```kotlin
enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}
```

#### Repository
**Localização**: `data/repository/`

Repositórios são a ponte entre a camada de dados e a camada de domínio.

```kotlin
class OnboardingRepository(
    private val onboardingApi: OnboardingApi
) {
    suspend fun getDreamTypes(): Result<List<DreamTypeDto>, NetworkError> {
        return onboardingApi.getDreamTypes()
    }
}
```

**Convenções:**
- Injete a API interface (não a implementação)
- Pode conter lógica de cache ou combinação de múltiplas fontes de dados
- Retorna `Result<T, NetworkError>`

---

### 2. Camada de Domínio (Domain Layer)

Esta camada contém a lógica de negócio pura, sem dependências de framework.

#### Modelos de Domínio
**Localização**: `domain/model/`

```kotlin
data class DreamTypeModel(
    val type: DreamType,
    val label: String
)

enum class DreamType(val key: String) {
    HOME("HOME"),
    TRAVEL("TRAVEL"),
    MONEY("MONEY"),
    CAR("CAR"),
    STUDY("STUDY"),
    WEDDING("WEDDING"),
    VACATION("VACATION"),
    HEALTH("HEALTH"),
    OTHER("OTHER");

    companion object {
        fun fromKey(key: String): DreamType {
            return entries.find { it.key == key } ?: OTHER
        }
    }
}
```

**Convenções:**
- Representa conceitos de negócio
- Sem anotações de serialização
- Sufixo: `Model` (para diferenciar de DTOs)
- Use enums para valores fixos

#### Use Cases
**Localização**: `domain/usecase/`

Use Cases contêm a lógica de negócio específica. Cada use case faz **uma coisa** bem feita.

```kotlin
class GetDreamTypesUseCase(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke(): Result<List<DreamTypeModel>, NetworkError> {
        return onboardingRepository.getDreamTypes().map { dtoList ->
            dtoList.map { dto ->
                DreamTypeModel(
                    type = DreamType.fromKey(dto.key),
                    label = dto.label
                )
            }
        }
    }
}
```

**Convenções:**
- Nome: `[Verb][Noun]UseCase` (ex: `GetDreamTypesUseCase`)
- Implementa `operator fun invoke()` para ser chamado como função
- Converte DTOs em Models de domínio
- Injete apenas repositórios ou outros use cases

---

### 3. Camada de Apresentação (Presentation Layer)

#### Organização por Feature
Cada feature tem sua própria pasta com:
- **view/**: Composables (UI)
- **viewmodel/**: ViewModels
- **state/**: Estados da UI
- **action/**: Ações do usuário
- **effect/**: Efeitos colaterais (navegação, toasts)
- **model/**: Modelos específicos da UI
- **mapper/**: Mappers (Domain → UI)

#### UI State
**Localização**: `presentation/[feature]/state/`

Estados representam todas as possibilidades da tela.

```kotlin
sealed interface DreamSelectionUiState {
    data object Loading : DreamSelectionUiState
    data class Resumed(val model: DreamSelectionUiModel) : DreamSelectionUiState
    data class Error(val message: String) : DreamSelectionUiState
}
```

**Convenções:**
- Use `sealed interface` ou `sealed class`
- Casos comuns: `Loading`, `Resumed` (sucesso), `Error`
- Sufixo: `UiState`

#### UI Models
**Localização**: `presentation/[feature]/model/`

Modelos otimizados para exibição na UI.

```kotlin
data class DreamSelectionUiModel(
    val items: List<DreamSelectionItemUiModel>
)

data class DreamSelectionItemUiModel(
    val type: DreamType,
    val label: String,
    val isSelected: Boolean
)
```

**Convenções:**
- Sufixo: `UiModel`
- Contém apenas dados necessários para a UI
- Pode incluir estado de UI (ex: `isSelected`, `isLoading`)

#### Actions
**Localização**: `presentation/[feature]/action/`

Actions representam as interações do usuário.

```kotlin
interface DreamSelectionAction {
    fun sendAction(action: Action)

    sealed interface Action {
        data object OnInit : Action
        data class OnItemClicked(val dreamType: DreamType) : Action
    }
}
```

**Convenções:**
- Interface com método `sendAction()`
- Sealed interface `Action` com todas as ações possíveis
- Nomes descritivos: `OnInit`, `OnItemClicked`, `OnButtonClicked`

#### ViewModel
**Localização**: `presentation/[feature]/viewmodel/`

```kotlin
class DreamSelectionViewModel(
    private val getDreamTypesUseCase: GetDreamTypesUseCase
) : ViewModel(), DreamSelectionAction {
    
    private val _uiState = MutableStateFlow<DreamSelectionUiState>(
        DreamSelectionUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    override fun sendAction(action: DreamSelectionAction.Action) {
        when (action) {
            is DreamSelectionAction.Action.OnInit -> onInit()
            is DreamSelectionAction.Action.OnItemClicked -> handleItemClick(action.dreamType)
        }
    }

    private fun onInit() {
        viewModelScope.launch {
            when (val result = getDreamTypesUseCase()) {
                is Result.Success -> {
                    _uiState.value = DreamSelectionUiState.Resumed(
                        model = result.data.toUiModel()
                    )
                }
                is Result.Error -> {
                    _uiState.value = DreamSelectionUiState.Error(
                        message = result.error.toUserMessage()
                    )
                }
            }
        }
    }
}
```

**Convenções:**
- Extends `ViewModel` e implementa a interface `Action`
- Use `StateFlow` para estados
- `_uiState` privado (MutableStateFlow), `uiState` público (StateFlow)
- Use `viewModelScope.launch` para coroutines
- Injete apenas use cases (não repositórios diretamente)
- Trate erros e converta para mensagens amigáveis

#### View (Composable)
**Localização**: `presentation/[feature]/view/`

```kotlin
@Composable
fun DreamSelectionScreen(
    navController: NavHostController,
    viewModel: DreamSelectionViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendAction(DreamSelectionAction.Action.OnInit)
    }

    when (uiState) {
        is DreamSelectionUiState.Loading -> BettrLoading()
        
        is DreamSelectionUiState.Resumed -> {
            val model = (uiState as DreamSelectionUiState.Resumed).model
            DreamSelectionContent(
                model = model,
                onItemClick = { dreamType ->
                    viewModel.sendAction(
                        DreamSelectionAction.Action.OnItemClicked(dreamType)
                    )
                }
            )
        }
        
        is DreamSelectionUiState.Error -> {
            val message = (uiState as DreamSelectionUiState.Error).message
            BettrGenericError(message = message)
        }
    }
}

@Composable
private fun DreamSelectionContent(
    model: DreamSelectionUiModel,
    onItemClick: (DreamType) -> Unit
) {
    // UI implementation
}
```

**Convenções:**
- Composable principal recebe `NavHostController` e `ViewModel`
- Use `koinViewModel()` para injetar o ViewModel
- Colete o estado com `collectAsState()`
- Use `LaunchedEffect(Unit)` para ações de inicialização
- Pattern: `when` para renderizar baseado no estado
- Extraia a lógica de UI em Composables privados
- Passe callbacks para interações do usuário

#### Mappers
**Localização**: `presentation/[feature]/mapper/`

Mappers convertem modelos de domínio em modelos de UI.

```kotlin
object DreamTypeToIconMapper {
    fun map(dreamType: DreamType): Int {
        return when (dreamType) {
            DreamType.HOME -> Res.drawable.house_emoji
            DreamType.TRAVEL -> Res.drawable.plane_emoji
            DreamType.MONEY -> Res.drawable.money_bag_emoji
            // ... outros casos
            DreamType.OTHER -> Res.drawable.other_emoji
        }
    }
}
```

**Convenções:**
- Use `object` para mappers stateless
- Método: `fun map(input): Output`
- Nomeação: `[From]To[To]Mapper`

---

### 4. Design System

#### Localização
`designsystem/`

#### Estrutura
```
designsystem/
├── components/        # Componentes reutilzáveis
│   ├── BettrButton.kt
│   ├── BettrChecklistCard.kt
���   ├── BettrSelectionCard.kt
���   ├── BettrHighlightBox.kt
│   ├── BettrPagination.kt
│   ├── BettrLoading.kt
│   └── BettrGenericError.kt
├── theme/
│   ├── Color.kt       # Paleta de cores
│   ├── Type.kt        # Estilos de tipografia
│   └── Theme.kt       # Configuração do tema
└── util/
    └── StyledText.kt  # Utilitários de texto
```

#### Cores
**Localização**: `designsystem/theme/Color.kt`

```kotlin
val BettrGreenDark = Color(0xFF047857)
val BettrGreenLight = Color(0xFF6EE7B7)
val BettrGreenLighter = Color(0xFFD1FAE5)
val BettrGrayDark = Color(0xFF374151)
val BettrGrayLight = Color(0xFFD1D5DB)
```

**Convenções:**
- Prefixo: `Bettr` + nome descritivo
- Use `Color(0xFFRRGGBB)` para definir cores

#### Tipografia
**Localização**: `designsystem/theme/Type.kt`

```kotlin
object BettrTextStyles {
    @Composable
    fun titleLarge() = TextStyle(
        fontFamily = FontFamily(Font(Res.font.Inter_18pt_Regular)),
        fontSize = 30.sp,
        fontWeight = FontWeight.Normal
    )

    @Composable
    fun bodyLarge() = TextStyle(
        fontFamily = FontFamily(Font(Res.font.Inter_18pt_Regular)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )
}
```

#### Componentes
Todos os componentes do design system devem:
- Ser reutilizáveis
- Ter prefixo `Bettr`
- Ter função de preview com `@Preview`
- Ser stateless (receber dados por parâmetros)

Exemplo:
```kotlin
@Composable
fun BettrButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: BettrButtonVariant = BettrButtonVariant.PRIMARY,
    enabled: Boolean = true
) {
    // Implementation
}

enum class BettrButtonVariant {
    PRIMARY, SECONDARY, TEXT
}
```

---

### 5. Dependency Injection (Koin)

#### Módulos

**NetworkModule** (`di/NetworkModule.kt`):
```kotlin
val networkModule = module {
    single<HttpClient> { HttpClientFactory.create() }
    single { BettrClient() }
    single<OnboardingApi> { OnboardingApiImpl(get(), get()) }
    single { OnboardingRepository(get()) }
}
```

**AppModule** (`di/AppModule.kt`):
```kotlin
val appModule = module {
    // Use Cases
    factory { GetDreamTypesUseCase(get()) }

    // ViewModels
    single { WelcomeViewModel() }
    factory { DreamSelectionViewModel(get()) }
}
```

**Convenções:**
- `single`: Singleton (uma única instância)
- `factory`: Nova instância a cada injeção
- Use `get()` para resolver dependências automaticamente

#### Inicialização
**Localização**: `di/KoinInit.kt`

```kotlin
fun initKoin() {
    startKoin {
        modules(networkModule, appModule)
    }
}
```

---

### 6. Navegação

**Localização**: `navigation/`

#### Rotas
```kotlin
@Serializable
sealed interface Route {
    @Serializable
    data object Welcome : Route
    
    @Serializable
    data object DreamSelection : Route
    
    @Serializable
    data object BetTypes : Route
}
```

#### NavHost
```kotlin
@Composable
fun BettrNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Welcome
    ) {
        composable<Route.Welcome> {
            WelcomeScreen(navController = navController)
        }
        
        composable<Route.DreamSelection> {
            DreamSelectionScreen(navController = navController)
        }
    }
}
```

**Navegação na ViewModel:**
```kotlin
// Na tela de origem
navController.navigate(Route.DreamSelection)
```

**⚠️ IMPORTANTE: NavHost NÃO deve usar Use Cases**

Navigation components (NavHost, NavController) devem apenas:
- ✅ Definir rotas
- ✅ Compor telas
- ✅ Passar argumentos de navegação
- ✅ Fornecer callbacks de navegação

Navigation components NÃO devem:
- ❌ Injetar use cases
- ❌ Acessar camada de domínio/dados
- ❌ Tomar decisões baseadas em lógica de negócio
- ❌ Fazer queries de dados para determinar navegação

**Padrão Correto:**
```kotlin
// ❌ ERRADO: NavHost acessando use case
@Composable
fun BettrNavHost(
    navController: NavHostController,
    getDreamsUseCase: GetDreamsUseCase = koinInject() // ❌ ERRADO
) {
    val totalDreams = getDreamsUseCase() // ❌ ERRADO
    // ...
}

// ✅ CORRETO: Screen/ViewModel gerencia lógica
@Composable
fun DreamSettingsScreen(
    currentIndex: Int,
    onNavigateToNext: () -> Unit,
    onNavigateToComplete: () -> Unit,
    getDreamsUseCase: GetDreamsUseCase = koinInject() // ✅ CORRETO
) {
    val totalDreams = getDreamsUseCase() // ✅ CORRETO
    
    val handleContinue = {
        if (currentIndex + 1 < totalDreams) {
            onNavigateToNext()
        } else {
            onNavigateToComplete()
        }
    }
}
```

---

### 7. Cache e Gerenciamento de Estado

**Localização**: `data/cache/`

Para fluxos que precisam manter estado temporário (como onboarding), use um cache em memória com use cases para abstrair o acesso.

#### OnboardingCache
```kotlin
class OnboardingCache {
    private var selectedDreams: List<DreamTypeModel> = emptyList()
    private var configuredDreams: MutableMap<Int, DreamConfigurationModel> = mutableMapOf()
    
    fun setSelectedDreams(dreams: List<DreamTypeModel>) {
        selectedDreams = dreams
        configuredDreams.clear()
        dreams.forEachIndexed { index, _ ->
            configuredDreams[index] = DreamConfigurationModel()
        }
    }
    
    fun getDreamByIndex(index: Int): DreamTypeModel? = selectedDreams.getOrNull(index)
    
    fun saveDreamConfiguration(index: Int, configuration: DreamConfigurationModel) {
        configuredDreams[index] = configuration
    }
    
    fun clear() {
        selectedDreams = emptyList()
        configuredDreams.clear()
    }
}
```

#### Data Models para Cache
```kotlin
data class DreamConfigurationModel(
    val targetAmount: Double? = null,
    val targetDate: String? = null
) {
    fun isComplete(): Boolean = targetAmount != null && targetDate != null
}

data class ConfiguredDreamModel(
    val dreamType: DreamTypeModel,
    val configuration: DreamConfigurationModel
)
```

#### Use Cases para Cache
**⚠️ IMPORTANTE:** ViewModels NUNCA devem acessar cache diretamente. Use use cases!

```kotlin
// SetSelectedDreamsUseCase.kt
class SetSelectedDreamsUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(dreams: List<DreamTypeModel>) {
        onboardingCache.setSelectedDreams(dreams)
    }
}

// GetDreamByIndexUseCase.kt
class GetDreamByIndexUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(index: Int): DreamTypeModel? {
        return onboardingCache.getDreamByIndex(index)
    }
}

// SaveDreamConfigurationUseCase.kt
class SaveDreamConfigurationUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(index: Int, targetAmount: Double, targetDate: String) {
        val configuration = DreamConfigurationModel(
            targetAmount = targetAmount,
            targetDate = targetDate
        )
        onboardingCache.saveDreamConfiguration(index, configuration)
    }
}
```

#### Registrando no DI
```kotlin
val appModule = module {
    // Cache como singleton
    single { OnboardingCache() }
    
    // Use cases para cache
    factory { SetSelectedDreamsUseCase(get()) }
    factory { GetDreamByIndexUseCase(get()) }
    factory { SaveDreamConfigurationUseCase(get()) }
    
    // ViewModels usam use cases, não cache diretamente
    factory { DreamSelectionViewModel(get(), get()) } // getDreamTypesUseCase, setSelectedDreamsUseCase
}
```

#### Usando no ViewModel
```kotlin
class DreamSelectionViewModel(
    private val getDreamTypesUseCase: GetDreamTypesUseCase,
    private val setSelectedDreamsUseCase: SetSelectedDreamsUseCase // ✅ Use case, não cache
) : ViewModel() {
    
    private fun handleClickContinue() {
        val selectedDreams = // ... coletar dreams selecionados
        
        // ✅ CORRETO: Usar use case
        setSelectedDreamsUseCase(selectedDreams)
        
        // ❌ ERRADO: Acessar cache diretamente
        // onboardingCache.setSelectedDreams(selectedDreams)
    }
}
```

**Benefícios desta abordagem:**
- ✅ ViewModels não dependem de entidades da camada de dados
- ✅ Separação clara de camadas
- ✅ Fácil de testar (mock use cases)
- ✅ Fácil de trocar implementação de cache

**Fluxo de Dados:**
```
UI → ViewModel → Use Case → Cache/Repository → Data Layer
```

---


## 🚀 Guia Prático: Como Adicionar Novas Features

### Cenário: Adicionar endpoint "GET /user-profile"

Siga este passo a passo:

### **Passo 1: Criar o DTO**
**Arquivo**: `data/network/dto/UserProfileDto.kt`

```kotlin
package org.example.bettr.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("avatar_url") val avatarUrl: String?
)
```

### **Passo 2: Adicionar método na API Interface**
**Arquivo**: `data/network/api/OnboardingApi.kt` (ou criar nova API)

```kotlin
interface UserApi {
    suspend fun getUserProfile(userId: String): Result<UserProfileDto, NetworkError>
}
```

### **Passo 3: Implementar a API**
**Arquivo**: `data/network/api/UserApiImpl.kt`

```kotlin
package org.example.bettr.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.bettr.data.network.BettrClient
import org.example.bettr.data.network.dto.UserProfileDto
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

class UserApiImpl(
    private val httpClient: HttpClient,
    private val bettrClient: BettrClient
) : UserApi {
    private companion object {
        const val BASE_URL = "https://bettr-production.up.railway.app"
    }

    override suspend fun getUserProfile(userId: String): Result<UserProfileDto, NetworkError> {
        return bettrClient.safeApiCall {
            httpClient.get("$BASE_URL/user-profile/$userId").body()
        }
    }
}
```

### **Passo 4: Criar o Repository**
**Arquivo**: `data/repository/UserRepository.kt`

```kotlin
package org.example.bettr.data.repository

import org.example.bettr.data.network.api.UserApi
import org.example.bettr.data.network.dto.UserProfileDto
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

class UserRepository(
    private val userApi: UserApi
) {
    suspend fun getUserProfile(userId: String): Result<UserProfileDto, NetworkError> {
        return userApi.getUserProfile(userId)
    }
}
```

### **Passo 5: Criar o Domain Model**
**Arquivo**: `domain/model/UserProfile.kt`

```kotlin
package org.example.bettr.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String?
)
```

### **Passo 6: Criar o Use Case**
**Arquivo**: `domain/usecase/GetUserProfileUseCase.kt`

```kotlin
package org.example.bettr.domain.usecase

import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result
import org.example.bettr.data.network.util.map
import org.example.bettr.data.repository.UserRepository
import org.example.bettr.domain.model.UserProfile

class GetUserProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Result<UserProfile, NetworkError> {
        return userRepository.getUserProfile(userId).map { dto ->
            UserProfile(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                avatarUrl = dto.avatarUrl
            )
        }
    }
}
```

### **Passo 7: Criar os Modelos de UI**
**Arquivo**: `presentation/profile/model/UserProfileUiModel.kt`

```kotlin
package org.example.bettr.presentation.profile.model

data class UserProfileUiModel(
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val initials: String // Calculado para fallback do avatar
)
```

### **Passo 8: Criar o UI State**
**Arquivo**: `presentation/profile/state/UserProfileUiState.kt`

```kotlin
package org.example.bettr.presentation.profile.state

import org.example.bettr.presentation.profile.model.UserProfileUiModel

sealed interface UserProfileUiState {
    data object Loading : UserProfileUiState
    data class Resumed(val profile: UserProfileUiModel) : UserProfileUiState
    data class Error(val message: String) : UserProfileUiState
}
```

### **Passo 9: Criar Actions**
**Arquivo**: `presentation/profile/action/UserProfileAction.kt`

```kotlin
package org.example.bettr.presentation.profile.action

interface UserProfileAction {
    fun sendAction(action: Action)

    sealed interface Action {
        data class OnInit(val userId: String) : Action
        data object OnRefresh : Action
        data object OnEditClicked : Action
    }
}
```

### **Passo 10: Criar ViewModel**
**Arquivo**: `presentation/profile/viewmodel/UserProfileViewModel.kt`

```kotlin
package org.example.bettr.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result
import org.example.bettr.domain.usecase.GetUserProfileUseCase
import org.example.bettr.presentation.profile.action.UserProfileAction
import org.example.bettr.presentation.profile.model.UserProfileUiModel
import org.example.bettr.presentation.profile.state.UserProfileUiState

class UserProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel(), UserProfileAction {

    private val _uiState = MutableStateFlow<UserProfileUiState>(UserProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    override fun sendAction(action: UserProfileAction.Action) {
        when (action) {
            is UserProfileAction.Action.OnInit -> loadProfile(action.userId)
            is UserProfileAction.Action.OnRefresh -> refreshProfile()
            is UserProfileAction.Action.OnEditClicked -> handleEditClicked()
        }
    }

    private fun loadProfile(userId: String) {
        viewModelScope.launch {
            _uiState.value = UserProfileUiState.Loading
            
            when (val result = getUserProfileUseCase(userId)) {
                is Result.Success -> {
                    val profile = result.data
                    _uiState.value = UserProfileUiState.Resumed(
                        profile = UserProfileUiModel(
                            name = profile.name,
                            email = profile.email,
                            avatarUrl = profile.avatarUrl,
                            initials = profile.name.split(" ")
                                .mapNotNull { it.firstOrNull()?.uppercase() }
                                .take(2)
                                .joinToString("")
                        )
                    )
                }
                is Result.Error -> {
                    _uiState.value = UserProfileUiState.Error(
                        message = when (result.error) {
                            NetworkError.NO_INTERNET -> "Sem conexão com a internet"
                            NetworkError.SERVER_ERROR -> "Erro no servidor"
                            else -> "Erro ao carregar perfil"
                        }
                    )
                }
            }
        }
    }

    private fun refreshProfile() {
        // Implementar lógica de refresh
    }

    private fun handleEditClicked() {
        // Implementar navegação para edição
    }
}
```

### **Passo 11: Criar a View (Screen)**
**Arquivo**: `presentation/profile/view/UserProfileScreen.kt`

```kotlin
package org.example.bettr.presentation.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrGenericError
import org.example.bettr.designsystem.components.BettrLoading
import org.example.bettr.presentation.profile.action.UserProfileAction
import org.example.bettr.presentation.profile.state.UserProfileUiState
import org.example.bettr.presentation.profile.viewmodel.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    userId: String,
    navController: NavHostController,
    viewModel: UserProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.sendAction(UserProfileAction.Action.OnInit(userId))
    }

    when (val state = uiState) {
        is UserProfileUiState.Loading -> {
            BettrLoading()
        }
        
        is UserProfileUiState.Resumed -> {
            UserProfileContent(
                profile = state.profile,
                onEditClick = {
                    viewModel.sendAction(UserProfileAction.Action.OnEditClicked)
                }
            )
        }
        
        is UserProfileUiState.Error -> {
            BettrGenericError(
                message = state.message,
                onRetry = {
                    viewModel.sendAction(UserProfileAction.Action.OnInit(userId))
                }
            )
        }
    }
}

@Composable
private fun UserProfileContent(
    profile: UserProfileUiModel,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        if (profile.avatarUrl != null) {
            // Carregar imagem do avatar
        } else {
            // Mostrar iniciais
            Text(text = profile.initials)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(text = profile.name)
        Text(text = profile.email)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        BettrButton(
            text = "Editar Perfil",
            onClick = onEditClick
        )
    }
}
```

### **Passo 12: Registrar no Koin**
**Arquivo**: `di/NetworkModule.kt`

```kotlin
val networkModule = module {
    // ... existing code ...
    single<UserApi> { UserApiImpl(get(), get()) }
    single { UserRepository(get()) }
}
```

**Arquivo**: `di/AppModule.kt`

```kotlin
val appModule = module {
    // ... existing code ...
    factory { GetUserProfileUseCase(get()) }
    factory { UserProfileViewModel(get()) }
}
```

### **Passo 13: Adicionar Rota**
**Arquivo**: `navigation/Route.kt`

```kotlin
@Serializable
sealed interface Route {
    // ... existing routes ...
    
    @Serializable
    data class UserProfile(val userId: String) : Route
}
```

**Arquivo**: `navigation/BettrNavHost.kt`

```kotlin
@Composable
fun BettrNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Welcome
    ) {
        // ... existing routes ...
        
        composable<Route.UserProfile> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.UserProfile>()
            UserProfileScreen(
                userId = route.userId,
                navController = navController
            )
        }
    }
}
```

### **Passo 14: Navegar para a nova tela**
De qualquer lugar do app:

```kotlin
navController.navigate(Route.UserProfile(userId = "123"))
```

---

## 📚 Exemplos Completos

### Exemplo 1: Endpoint POST com Body

**API Implementation:**
```kotlin
override suspend fun createBet(betData: CreateBetDto): Result<BetDto, NetworkError> {
    return bettrClient.safeApiCall {
        httpClient.post("$BASE_URL/bets") {
            contentType(ContentType.Application.Json)
            setBody(betData)
        }.body()
    }
}
```

**DTO:**
```kotlin
@Serializable
data class CreateBetDto(
    @SerialName("user_id") val userId: String,
    @SerialName("amount") val amount: Double,
    @SerialName("dream_type") val dreamType: String
)

@Serializable
data class BetDto(
    @SerialName("id") val id: String,
    @SerialName("created_at") val createdAt: String
)
```

### Exemplo 2: Endpoint com Query Parameters

```kotlin
override suspend fun searchDreams(
    query: String,
    limit: Int
): Result<List<DreamTypeDto>, NetworkError> {
    return bettrClient.safeApiCall {
        httpClient.get("$BASE_URL/dreams/search") {
            parameter("query", query)
            parameter("limit", limit)
        }.body()
    }
}
```

### Exemplo 3: Endpoint com Headers (Autenticação)

```kotlin
override suspend fun getProtectedData(token: String): Result<DataDto, NetworkError> {
    return bettrClient.safeApiCall {
        httpClient.get("$BASE_URL/protected") {
            header("Authorization", "Bearer $token")
        }.body()
    }
}
```

---

## 🔍 Referências de Código

### Fluxo Completo de Dados

```
UI (Composable)
    ↓ sendAction()
ViewModel
    ↓ invoke()
UseCase
    ↓ método do repository
Repository
    ↓ método da API
API Implementation
    ↓ safeApiCall()
BettrClient → HttpClient → Backend
    ↓
Result<DTO, NetworkError>
    ↓ retorna
API Implementation
    ↓ retorna
Repository
    ↓ map() - converte DTO → Model
UseCase
    ↓ atualiza _uiState
ViewModel
    ↓ collectAsState()
UI (Composable) - renderiza
```

### Checklist para Nova Feature

- [ ] **Data Layer**
  - [ ] Criar DTO com `@Serializable`
  - [ ] Adicionar método na API interface
  - [ ] Implementar na API implementation usando `BettrClient.safeApiCall()`
  - [ ] Criar repository

- [ ] **Domain Layer**
  - [ ] Criar model de domínio
  - [ ] Criar use case
  - [ ] Converter DTO → Model no use case

- [ ] **Presentation Layer**
  - [ ] Criar UI Model
  - [ ] Criar UI State (Loading, Resumed, Error)
  - [ ] Criar Actions
  - [ ] Criar ViewModel
  - [ ] Criar Screen (Composable)

- [ ] **Dependency Injection**
  - [ ] Registrar API no NetworkModule
  - [ ] Registrar Repository no NetworkModule
  - [ ] Registrar UseCase no AppModule
  - [ ] Registrar ViewModel no AppModule

- [ ] **Navigation** (se necessário)
  - [ ] Adicionar Route
  - [ ] Adicionar composable no NavHost

---

## 🎨 Boas Práticas

### 1. Sempre use Type Safety
❌ **Evite:**
```kotlin
fun navigate(route: String)
```

✅ **Prefira:**
```kotlin
fun navigate(route: Route)
```

### 2. Não exponha MutableStateFlow
❌ **Evite:**
```kotlin
val uiState = MutableStateFlow<UiState>(UiState.Loading)
```

✅ **Prefira:**
```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
val uiState = _uiState.asStateFlow()
```

### 3. ViewModels não devem conhecer Repositories diretamente
❌ **Evite:**
```kotlin
class MyViewModel(private val repository: MyRepository)
```

✅ **Prefira:**
```kotlin
class MyViewModel(private val myUseCase: MyUseCase)
```

### 4. Componentes devem ser Stateless
❌ **Evite:**
```kotlin
@Composable
fun MyComponent() {
    var isSelected by remember { mutableStateOf(false) }
    // ...
}
```

✅ **Prefira:**
```kotlin
@Composable
fun MyComponent(
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {
    // ...
}
```

### 5. Use LaunchedEffect para inicialização
```kotlin
LaunchedEffect(Unit) {
    viewModel.sendAction(MyAction.Action.OnInit)
}
```

### 6. Trate todos os casos de erro
```kotlin
when (result.error) {
    NetworkError.NO_INTERNET -> "Sem conexão"
    NetworkError.SERVER_ERROR -> "Erro no servidor"
    NetworkError.SERIALIZATION -> "Erro ao processar dados"
    NetworkError.REQUEST_TIMEOUT -> "Tempo esgotado"
    NetworkError.UNAUTHORIZED -> "Não autorizado"
    NetworkError.CONFLICT -> "Conflito"
    NetworkError.TOO_MANY_REQUESTS -> "Muitas requisições"
    NetworkError.PAYLOAD_TOO_LARGE -> "Dados muito grandes"
    NetworkError.UNKNOWN -> "Erro desconhecido"
}
```

---

## 🐛 Troubleshooting

### "ClassNotFoundException: BettrApplication"
**Solução**: Limpar e rebuildar o projeto:
```bash
./gradlew clean
./gradlew build
```

### Infinite Loading na tela
**Causas possíveis:**
1. Permissão de internet não adicionada no `AndroidManifest.xml`
2. Exception não tratada no use case ou repository
3. Problema de serialização

**Debug:**
```kotlin
// Adicione logs em cada camada
println("ViewModel: Calling use case")
println("UseCase: Got result = $result")
println("Repository: API returned = $response")
```

### Erro de serialização
**Solução**: Verifique se:
- DTO tem `@Serializable`
- Nomes dos campos batem com o JSON (use `@SerialName`)
- Tipos dos campos são compatíveis

### Koin: No definition found
**Solução**: 
1. Verifique se registrou no módulo correto
2. Verifique se o módulo está sendo carregado no `initKoin()`
3. Use `get()` para resolver dependências automaticamente

---

## 📖 Glossário

- **DTO (Data Transfer Object)**: Objeto que representa dados da API
- **Model**: Objeto de domínio com lógica de negócio
- **UI Model**: Objeto otimizado para exibição na UI
- **UseCase**: Lógica de negócio específica
- **Repository**: Abstração da fonte de dados
- **ViewModel**: Gerenciador de estado e lógica de apresentação
- **State**: Representação do estado atual da UI
- **Action**: Interação do usuário com a UI
- **Effect**: Efeito colateral (navegação, toast, etc)
- **Composable**: Função que desenha UI
- **StateFlow**: Fluxo reativo de estados
- **LaunchedEffect**: Executa código quando composable é criado
- **Koin**: Framework de injeção de dependências
- **Result**: Tipo que representa sucesso ou erro
- **NetworkError**: Enum com tipos de erros de rede

---

## 📞 Suporte

Para dúvidas ou problemas:
1. Revise esta documentação
2. Veja exemplos de código existente no projeto
3. Consulte a documentação oficial:
   - [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
   - [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
   - [Ktor](https://ktor.io/docs/client.html)
   - [Koin](https://insert-koin.io/)

---

**Última atualização**: Dezembro 2025
**Versão**: 1.0


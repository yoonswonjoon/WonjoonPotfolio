package com.vlm.wonjoonpotfolio.ui.iAm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.vlm.wonjoonpotfolio.data.project.evaluate.ProjectEvaluateData
import com.vlm.wonjoonpotfolio.data.project.evaluate.ProjectEvaluateRepository
import com.vlm.wonjoonpotfolio.data.useCase.GetProject
import com.vlm.wonjoonpotfolio.data.useCase.GetUserUseCase
import com.vlm.wonjoonpotfolio.data.useCase.LoginCheckUserCase
import com.vlm.wonjoonpotfolio.data.user.UserForUi
import com.vlm.wonjoonpotfolio.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject



enum class ProjectDetailPage{
    DetailPage,
    EvaluationPage,
    EditEvaluationPage
}


data class ProjectViewState(
    val list: List<UserForUi> = listOf(),
    val selectedUser: UserForUi? = null,
    val stackMap: Map<String, List<ProjectStringType>>? = null,
    val isLoading: Boolean = true,
    val errors: List<PortfolioError> = listOf(),
    val currentDetailRoute: ProjectDetailPage = ProjectDetailPage.DetailPage,
    val evaluationDialogShow : Boolean = false,
    val evaluateCancelDialogShow : Boolean = false,
    val evaluationData : ProjectEvaluateData = ProjectEvaluateData(),
    val evaluateProcess : Boolean = false,
    val amIin : Boolean? = null,
    val evaluateList : List<ProjectEvaluateData> = listOf(),
    val myEvaluateData: ProjectEvaluateData = ProjectEvaluateData(),
    val totalPoint : Double = 0.0,
    val totalParticipant : Int = 0
)

@HiltViewModel
class ProjectViewModel
@Inject
constructor(
    private val getUserUseCase: GetUserUseCase,
    private val loginCheckUserCase: LoginCheckUserCase,
    private val projectEvaluateRepository: ProjectEvaluateRepository,
    private val getProject: GetProject
) :
    ViewModel() {
    private val _viewState = MutableStateFlow(
        ProjectViewState()
    )

    val viewState
        get() = _viewState.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _viewState.value
        )


    fun initProjectView(list: List<String>, stacks: Map<String, String>? = null) {
        val map = mutableMapOf<String, List<ProjectStringType>>()
        stacks?.forEach { key, value ->
            map[key] = value.commaSplit().map { it.addUriToString() }
        }

        getUserUseCase(list).onEach { users ->
            _viewState.value = _viewState.value.copy(
                list = users,
                stackMap = map,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }

    fun selectUser(userForUi: UserForUi) {
        _viewState.value = _viewState.value.copy(
            selectedUser = userForUi
        )
    }

    fun evaluation(point: Int,msg : String) {
        if (point <= 0) {
            addErrors(
                error = PortfolioError(
                    showNoBtn = true,
                    msg = "1점 이상을 줘야합니다"
                )
            )
        } else {
            try {
                _viewState.value = _viewState.value.copy(
                    evaluationDialogShow = true,
                    evaluationData = ProjectEvaluateData(
                        eid = loginCheckUserCase.invoke()?.email!!,
                        point = point,
                        msg = msg,
                        uid = loginCheckUserCase.invoke()?.uid!!,
                    )
                )
            }catch (e:Exception){
                addErrors(PortfolioError(msg = "로그인이 필요한 기능입니다. 메인 화면에서 로그인을 해주세요"))
            }

        }
    }

    fun evaluationDialogDismiss() {
        _viewState.value = _viewState.value.copy(
            evaluationDialogShow = false,
        )
    }


    fun addErrors(error: PortfolioError) {
        val errors = _viewState.value.errors.toMutableList()
        errors.add(error)
        _viewState.value = _viewState.value.copy(errors = listOf())
        _viewState.value = _viewState.value.copy(errors = errors)
    }

    fun dismissError(error: PortfolioError) {
        val errors = _viewState.value.errors.toMutableList()
        errors.removeFirst()
        _viewState.value = _viewState.value.copy(errors = listOf())
        _viewState.value = _viewState.value.copy(errors = errors)
    }

    fun openEvaluation() {
        _viewState.value =
            _viewState.value.copy(currentDetailRoute = ProjectDetailPage.EvaluationPage)
    }

    fun closeEvaluation() {
        _viewState.value = _viewState.value.copy(
            currentDetailRoute = ProjectDetailPage.DetailPage,
            evaluationData =  ProjectEvaluateData()
        )
    }


    fun proceedEvaluation(projectUid : String) {
        if(loginCheckUserCase.invoke() != null){
            projectEvaluateRepository.writeEvaluate(
                uid = _viewState.value.evaluationData.uid,
                projectId = projectUid,
                evaluate = _viewState.value.evaluationData
            ).onEach { result ->
                when(result){
                    is ResultState.Loading ->{
                        _viewState.value = _viewState.value.copy(evaluateProcess = true)
                    }
                    is ResultState.Success ->{
                        if(result.data){
                            loadEvaluateList(projectUid = projectUid, forced = true, userEid = _viewState.value.evaluationData.eid)
                            _viewState.value = _viewState.value.copy(evaluateProcess = false , amIin = true, myEvaluateData = _viewState.value.evaluationData)
                            addErrors(PortfolioError(msg = "정상 등록 되었습니다."))
                        }else{
                            _viewState.value = _viewState.value.copy(evaluateProcess = false)
                            addErrors(PortfolioError(msg = "실패"))
                        }
                    }
                    is ResultState.Error ->{
                        _viewState.value = _viewState.value.copy(evaluateProcess = false)
                        addErrors(PortfolioError(msg = result.message))
                    }
                }
            }.launchIn(viewModelScope)
        }else{
            addErrors(PortfolioError(msg = "로그인이 필요한 기능입니다. 메인 화면에서 로그인을 해주세요"))
        }
        _viewState.value = _viewState.value.copy(evaluationDialogShow = false)
    }

    fun amIin(projectUid: String){
        if(_viewState.value.amIin ==null){
            try {
                val user = loginCheckUserCase.invoke()!!
                projectEvaluateRepository.amIin(user.uid,projectUid)
                    .onEach {
                        _viewState.value = _viewState.value.copy(amIin = it)
                        if(it == true) loadEvaluateList(userEid = user.email!!, projectUid = projectUid, forced = true)
                    }
                    .launchIn(viewModelScope)
            }catch (e:Exception){

            }
        }
    }

    fun loadEvaluateList(
        userEid : String,
        forced : Boolean = true,
        projectUid: String
    ){
        // 로드하는데 이미 데이터 있으면 무시 등등 기능 추가
        if(forced || (_viewState.value.evaluateList.isEmpty() && !forced)){
            viewModelScope.launch {
                getProject.invoke(projectUid)?.let{
                    val point = it.accPoint.toDouble()/it.participantCount.toDouble()
                    _viewState.value = _viewState.value.copy(totalPoint = point , totalParticipant = it.participantCount)
                }
            }

            projectEvaluateRepository.getAllEvaluate(projectUid).onEach {  result ->
                when(result){
                    is ResultState.Loading ->{
                        _viewState.value = _viewState.value.copy(evaluateProcess = true)
                    }
                    is ResultState.Success ->{
                        _viewState.value = _viewState.value.copy(evaluateProcess = false, evaluateList = result.data, myEvaluateData = result.data.filter { it.eid == userEid }.first())
                    }
                    is ResultState.Error ->{
                        _viewState.value = _viewState.value.copy(evaluateProcess = false)
                        addErrors(PortfolioError(msg = result.message))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateProject(){

    }
}
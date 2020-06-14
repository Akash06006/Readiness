package com.example.fleet.viewmodels.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.example.fleet.model.home.QuestionData
import com.example.fleet.model.home.QuestionInput
import com.example.fleet.repositories.questions.QuestionsRepository
import com.example.fleet.viewmodels.BaseViewModel

class QuestionsViewModel : BaseViewModel() {
    private var getQuestionsData = MutableLiveData<QuestionData>()
    private var saveAnswerResponse = MutableLiveData<CommonModel>()
    private var loginRepository = QuestionsRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnected()) {
            getQuestionsData = loginRepository.getQuestions()
            saveAnswerResponse = loginRepository.saveAnswer(null)
        }
    }

    fun getQuestionsRes() : LiveData<QuestionData> {
        return getQuestionsData
    }

    fun getAnwersRes() : LiveData<CommonModel> {
        return saveAnswerResponse
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]

    }

    fun saveAnswer(inputModel : ArrayList<QuestionInput.AnswerInputModel>) {
        if (UtilsFunctions.isNetworkConnected()) {
            //emialExistenceResponse = loginRepository.checkPhoneExistence(mJsonObject)
            saveAnswerResponse = loginRepository.saveAnswer(inputModel)
            mIsUpdating.postValue(true)
        }

    }

}
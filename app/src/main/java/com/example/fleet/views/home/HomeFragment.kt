package com.example.fleet.views.home

import android.app.Dialog
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.common.UtilsFunctions.showToastError
import com.example.fleet.common.UtilsFunctions.showToastSuccess
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.FragmentHomeBinding
import com.example.fleet.model.CommonModel
import com.example.fleet.model.home.AnswerInputModel
import com.example.fleet.model.home.QuestionData
import com.example.fleet.model.home.QuestionInput
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseFragment
import com.example.fleet.utils.DialogClass
import com.example.fleet.viewmodels.questions.QuestionsViewModel
import com.example.fleet.views.authentication.LoginActivity
import com.google.android.gms.location.*
import com.google.gson.JsonObject
import com.uniongoods.adapters.QuestionsListAdapter

class HomeFragment : BaseFragment() {
    private var questionList = ArrayList<QuestionData.Data>()
    private var totalQuestionList = ArrayList<QuestionData.Data>()
    private var questionInputModel = QuestionInput()

    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private lateinit var homeViewModel : QuestionsViewModel

    private var sharedPrefClass : SharedPrefClass? = null

    var page = 1

    var questionsListAdapter : QuestionsListAdapter? = null
    var surveyId = 1

    override fun getLayoutResId() : Int {
        return R.layout.fragment_home
    }

    override fun onResume() {
        super.onResume()

    }

    override fun initView() {
        fragmentHomeBinding = viewDataBinding as FragmentHomeBinding
        homeViewModel = ViewModelProviders.of(this).get(QuestionsViewModel::class.java)
        fragmentHomeBinding.homeViewModel = homeViewModel

        totalQuestionList.clear()
        sharedPrefClass = SharedPrefClass()
        val userId = sharedPrefClass!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.USERID
        ).toString()
        val address = sharedPrefClass!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.POC_ADDRESS
        ).toString()

        fragmentHomeBinding.txtAddress.setText(address)

        val surveyId = sharedPrefClass!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.SURVEY_ID
        ).toString()

        if (UtilsFunctions.isNetworkConnected()) {
            baseActivity.startProgressDialog()
        }
        page = 1


        homeViewModel.getQuestionsRes().observe(this,
            Observer<QuestionData> { response->
                baseActivity.stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.statusCode == 200 -> {
                            if (response.data != null && response.data?.size!! > 0) {
                                fragmentHomeBinding.tvQuestionCount.setText("10/20")
                                totalQuestionList.addAll(response.data!!)
                                if (totalQuestionList.size > 10) {
                                    //  questionInputModel.clear()
                                    val data = ArrayList<AnswerInputModel>()
                                    for (item in 0 until 10) {
                                        questionList.add(totalQuestionList[item])
                                        val answerModel = AnswerInputModel()
                                        answerModel.answer = ""
                                        answerModel.questionId = totalQuestionList[item].id
                                        answerModel.userId = userId
                                        answerModel.siteDetailId = surveyId
                                        data.add(answerModel)
                                    }
                                    questionInputModel.data = data
                                }

                                // fragmentHomeBinding.rvJobs.visibility = View.VISIBLE
                                //fragmentHomeBinding.tvNoRecord.visibility = View.GONE
                                initRecyclerView()
                            }

                        }
                        response.statusCode == 401 -> {
                            showToastError("Session Expired, Please login again")
                            SharedPrefClass().putObject(
                                MyApplication.instance.applicationContext,
                                "isLogin",
                                false
                            )
                            val i = Intent(
                                MyApplication.instance.applicationContext,
                                LoginActivity::class.java
                            )
                            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            MyApplication.instance.applicationContext.startActivity(i)
                        }
                        else -> message?.let {
                            showToastError(it)
                            /*fragmentHomeBinding.rvJobs.visibility = View.GONE*/
                            //fragmentHomeBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }
                }
            })

        homeViewModel.getAnwersRes().observe(this,
            Observer<CommonModel> { response->
                baseActivity.stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.statusCode == 200 -> {
                            if (page != 2) {
                                //if (totalQuestionList.count() > (page * 10)) {
                                questionInputModel.data?.clear()
                                questionList.clear()
                                val data = ArrayList<AnswerInputModel>()
                                for (item in 10 until totalQuestionList.count()) {
                                    questionList.add(totalQuestionList[item])

                                    val answerModel = AnswerInputModel()
                                    answerModel.answer = ""
                                    answerModel.questionId = totalQuestionList[item].id
                                    answerModel.userId = userId
                                    answerModel.siteDetailId = surveyId
                                    // questionInputModel.add(answerModel)
                                    data.add(answerModel)
                                    page = 2
                                }
                                // page++
                                fragmentHomeBinding.tvNext.setText("Submit")
                                // fragmentHomeBinding.rlNext.setBackgroundResource(activity.resources.getDrawable(R.drawable))
                                questionInputModel.data = data
                                questionsListAdapter?.notifyDataSetChanged()
                                //
                                fragmentHomeBinding.scrolView.pageScroll(View.FOCUS_UP)
                                fragmentHomeBinding.rvQuesions.smoothScrollToPosition(0)
                                //if (page == 2)
                                fragmentHomeBinding.tvQuestionCount.setText("20/20")
                            } else {
                                showToastSuccess("Call Score Activity")
                            }
                            //}

                        }
                        response.statusCode == 401 -> {
                            showToastError("Session Expired, Please login again")
                            SharedPrefClass().putObject(
                                MyApplication.instance.applicationContext,
                                "isLogin",
                                false
                            )
                            val i = Intent(
                                MyApplication.instance.applicationContext,
                                LoginActivity::class.java
                            )
                            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            MyApplication.instance.applicationContext.startActivity(i)
                        }
                        else -> message?.let {
                            showToastError(it)
                            //fragmentHomeBinding.rvJobs.visibility = View.GONE
                            //fragmentHomeBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }
                }
            })

        homeViewModel!!.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {
                when (it) {


                    "rlNext" -> {
                        var allSelected = true
                        for (item in questionList) {
                            if (TextUtils.isEmpty(item.selected)) {
                                allSelected = false
                            }
                        }

                        if (!allSelected) {
                            showToastError("Please select all questions")
                        } else {
                            // page = 2
                            // 2.5

                            homeViewModel.saveAnswer(questionInputModel)


                        }
                    }

                }
            })
        )


    }

    private fun initRecyclerView() {
        questionsListAdapter = QuestionsListAdapter(this@HomeFragment, questionList)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentHomeBinding.rvQuesions.layoutManager = linearLayoutManager
        fragmentHomeBinding.rvQuesions.adapter = questionsListAdapter
        fragmentHomeBinding.rvQuesions.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    fun radioClick(clickedItem : String, id : String) {
        for (item in 0 until questionList.count()) {
            if (questionList[item].id.equals(id)) {
                questionList[item].selected = clickedItem
                questionInputModel.data?.get(item)?.answer = clickedItem
            }
        }
        questionsListAdapter?.notifyDataSetChanged()
    }

}
package com.example.fleet.views.home

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivityDashboardBinding
import com.example.fleet.model.CommonModel
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.DialogssInterface
import com.example.fleet.views.authentication.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class DashboardActivity : BaseActivity(),
    DialogssInterface {
    var activityDashboardBinding : ActivityDashboardBinding? = null
    private var navigationView : NavigationView? = null
    private var drawer : DrawerLayout? = null
    private var confirmationDialog : Dialog? = null
    private var ratingDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    var fragmentManager : FragmentManager? = null
    // private var dashboardViewModel : DashboardViewModel? = null
    private var removedFrag : String = ""
    var fragment : Fragment? = null
    //    companion object {
//        @get:Synchronized
//        lateinit var toolBarText : TextView
//        lateinit var toolBarImage : ImageView
//        var removedFrag : String = ""
//
//    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initViews() {
        activityDashboardBinding = viewDataBinding as ActivityDashboardBinding
        fragmentManager = supportFragmentManager
        //navigationView = activityDashboardBinding!!.navView
        //  navigationView!!.alpha = 0.9f

        // drawer = activityDashboardBinding!!.drawerLayout
        //activityDashboardBinding!!.tablayout.getTabAt(0)?.select()

        // dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        //   activityDashboardBinding!!.dashboardViewModel = dashboardViewModel
        // toolBarText = activityDashboardBinding!!.toolbarCommon.imgToolbarText
        // toolBarImage = activityDashboardBinding!!.toolbarCommon.imgRight
        /** Show Rating Dialog here**/
        // checkForRating(0)
        /*****************/
        // activityDashboardBinding!!.toolbarCommon.toolbar.setImageResource(R.drawable.ic_sidebar)
        // activityDashboardBinding!!.toolbarCommon.imgRight.visibility = View.VISIBLE
        // activityDashboardBinding!!.toolbarCommon.imgRight.setImageResource(R.drawable.ic_notifications)

        /* val image = SharedPrefClass().getPrefValue(
             MyApplication.instance.applicationContext,
             GlobalConstants.USER_IAMGE
         )
         // ic_profile
         Glide.with(this)
             .load(image)
             .placeholder(R.drawable.user)
             .into(activityDashboardBinding!!.icProfile)
         val name = SharedPrefClass().getPrefValue(
             MyApplication.instance.applicationContext,
             getString(R.string.first_name)
         )
         activityDashboardBinding!!.tvName.text = name.toString()*/
        // fragment = HomeFragment()
        // callFragments(fragment, supportFragmentManager, false, "send_data", "")
        //fragment = HomeFragment()
        // callFragments(fragment, supportFragmentManager, false, "send_data", "")
        replaceFragmetn(HomeFragment())
        /* when (fragmentType) {
             is HomeFragment -> {
                 // activityDashboardBinding!!.toolbarCommon.imgRight.visibility =
                 //     View.VISIBLE
             }
         }*/
        /*dashboardViewModel!!.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {
                when (it) {

                    "tv_nav_home" -> {
                        activityDashboardBinding!!.toolbarCommon.imgRight.visibility = View.VISIBLE
                        activityDashboardBinding!!.toolbarCommon.imgRight.setImageDrawable(
                            getDrawable(R.drawable.ic_notifications)
                        )
                        val fragment = HomeFragment()
                        activityDashboardBinding!!.toolbarCommon.imgToolbarText.text =
                            getString(R.string.home)
                        activityDashboardBinding!!.drawerLayout.closeDrawers()
                        this.callFragments(
                            fragment,
                            supportFragmentManager,
                            false,
                            "send_data",
                            ""
                        )
                        activityDashboardBinding!!.tablayout.getTabAt(0)?.select()
                        activityDashboardBinding!!.drawerLayout.closeDrawers()

                    }
                    "tv_nav_contact" -> {
                        *//* val intent = Intent(this, TrackingActivity::class.java)
                         startActivity(intent)*//*
                    }
                    "ic_profile" -> {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_account" -> {
                        val intent = Intent(this, MyAccountsActivity::class.java)
                        startActivity(intent)
                    }
                    "img_nav_cancel" -> {
                        activityDashboardBinding!!.drawerLayout.closeDrawers()
                    }
                    "tv_nav_logout" -> {
                        confirmationDialog = mDialogClass.setDefaultDialog(
                            this,
                            this,
                            "logout",
                            getString(R.string.warning_logout)
                        )
                        confirmationDialog?.show()

                    }
                    "toolbar" -> {
                        val image = SharedPrefClass().getPrefValue(
                            MyApplication.instance.applicationContext,
                            GlobalConstants.USER_IAMGE
                        )
                        // ic_profile
                        Glide.with(this)
                            .load(image)
                            .placeholder(R.drawable.user)
                            .into(activityDashboardBinding!!.icProfile)

                        val name = SharedPrefClass().getPrefValue(
                            MyApplication.instance.applicationContext,
                            getString(R.string.first_name)
                        )
                        activityDashboardBinding!!.tvName.text = name.toString()
                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        } else {
                            drawer!!.openDrawer(Gravity.LEFT)
                        }
                        val fragmentType =
                            supportFragmentManager.findFragmentById(R.id.frame_layout)
                        when (fragmentType) {
                            is HomeFragment -> {
                                activityDashboardBinding!!.toolbarCommon.imgRight.visibility =
                                    View.VISIBLE
                            }
                        }
                    }
                }
            })
        )*/


        /*activityDashboardBinding!!.tablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab?) {
                var fragment : Fragment? = null
                //   activityDashboardBinding!!.toolbarCommon.imgRight.visibility = View.GONE
                when (tab!!.position) {
                    0 -> fragment = HomeFragment()

                }
                callFragments(fragment, supportFragmentManager, false, "send_data", "")

                Handler().postDelayed({
                    setHeadings()
                }, 300)

            }

            override fun onTabUnselected(tab : TabLayout.Tab?) {

            }

            override fun onTabReselected(tab : TabLayout.Tab?) {
                //var fragment : Fragment? = null
                //Not In use
            }
        })*/

    }

    override fun getLayoutId() : Int {
        return R.layout.activity_dashboard
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setHeadings() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        when (fragment) {
            is HomeFragment -> {
                /* activityDashboardBinding!!.toolbarCommon.imgToolbarText.text =
                     getString(R.string.home)
                 getString(R.string.calendar)
 */
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        setHeadings()
        /*  if (GlobalConstants.selectedCheckedFragment == 100) {
              activityDashboardBinding!!.tablayout.getTabAt(GlobalConstants.selectedFragment)!!.select()
              GlobalConstants.selectedCheckedFragment = 0
          }*/
        /*val image = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            GlobalConstants.USER_IAMGE
        )
        // ic_profile
        Glide.with(this)
            .load(image)
            .placeholder(R.drawable.user)
            .into(activityDashboardBinding!!.icProfile)
        val name = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            getString(R.string.first_name)
        )
        activityDashboardBinding!!.tvName.text = name.toString()*/

    }

    override fun onDialogConfirmAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> {
                confirmationDialog?.dismiss()
                // dashboardViewModel!!.callLogoutApi()
                // dashboardViewModel!!.callLogoutApi()

            }
//            "rating" -> {
//                ratingDialog?.dismiss()
//            }
        }
    }

    override fun onDialogCancelAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> confirmationDialog?.dismiss()
            "rating" -> ratingDialog?.dismiss()
        }
    }


    fun replaceFragmetn(fragment : Fragment) {

        val fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, "h")
        fragmentTransaction.addToBackStack("h")
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
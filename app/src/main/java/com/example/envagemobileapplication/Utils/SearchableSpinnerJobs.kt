package com.example.envagemobileapplication.Utils


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.envagemobileapplication.Adapters.SpinnerRecyclerAdapter
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SearchJobsClientsResp.SearchJobsClientsResp
import com.example.envagemobileapplication.Oauth.TokenManager
import com.example.envagemobileapplication.R
import com.ezshifa.aihealthcare.network.ApiUtils
import com.leo.searchablespinner.interfaces.OnAnimationEnd
import com.leo.searchablespinner.interfaces.OnItemSelectListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("MemberVisibilityCanBePrivate", "RedundantSetter", "RedundantGetter")
class SearchableSpinnerJobs(private val context: Context) : LifecycleObserver {
    private val handler = Handler(Looper.getMainLooper())

    lateinit var onItemSelectListener: OnItemSelectListener
    private lateinit var dialog: AlertDialog
    var datalist: ArrayList<String> = ArrayList()
    var global = com.example.envagemobileapplication.Utils.Global
    private lateinit var dialogView: View
    var isfromsearch = false
    private lateinit var recyclerAdapter: com.example.envagemobileapplication.Adapters.SpinnerRecyclerAdapter

    var windowTopBackgroundColor: Int? = null
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var windowTitleTextColor: Int = ContextCompat.getColor(context, android.R.color.white)
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var negativeButtonBackgroundColor: Int? = null
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var negativeButtonTextColor: Int = ContextCompat.getColor(context, android.R.color.white)
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var animationDuration: Int = 420
    var showKeyboardByDefault: Boolean = true
    var spinnerCancelable: Boolean = false
    var windowTitle: String? = null
    var searchQueryHint: String = context.getString(android.R.string.search_go)
    var negativeButtonText: String = context.getString(android.R.string.cancel)
    var dismissSpinnerOnItemClick: Boolean = true
    var highlightSelectedItem: Boolean = true
    var negativeButtonVisibility: SpinnerView = SpinnerView.VISIBLE
    var windowTitleVisibility: SpinnerView = SpinnerView.GONE
    var searchViewVisibility: SpinnerView = SpinnerView.VISIBLE
    var selectedItemPosition: Int = -1
    var selectedItem: String? = null
    lateinit var textViewTitle: TextView
    lateinit var searchview: SearchView
    lateinit var buttonClose: Button
    lateinit var recyclerView: RecyclerView
    lateinit var headLayouts: RelativeLayout

    @Suppress("unused")
    enum class SpinnerView(val visibility: Int) {
        VISIBLE(View.VISIBLE),
        INVISIBLE(View.INVISIBLE),
        GONE(View.GONE)
    }

    init {
        initLifeCycleObserver()
    }

    fun show() {

        if (getDialogInfo(true)) {
            //clickedView = view
            dialogView = View.inflate(context, R.layout.searchable_spinner, null)
            textViewTitle = dialogView.findViewById(R.id.textViewTitle)
            searchview = dialogView.findViewById(R.id.searchView)
            buttonClose = dialogView.findViewById(R.id.buttonClose)
            recyclerView = dialogView.findViewById(R.id.recyclerView)
            headLayouts = dialogView.findViewById(R.id.headLayouts)
            val dialogBuilder =
                AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setCancelable(spinnerCancelable || negativeButtonVisibility != SpinnerView.VISIBLE)

            dialog = dialogBuilder.create()
            dialog.initView()
            initDialogColorScheme()
            dialog.show()
            dialog.initAlertDialogWindow()

        }
    }

    fun dismiss() {
        if (getDialogInfo(false))
            CircularReveal.startReveal(false, dialog, object : OnAnimationEnd {
                override fun onAnimationEndListener(isRevealed: Boolean) {
                    toggleKeyBoard(false)
                    if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(null)
                }
            }, animationDuration)
    }

    fun setSpinnerListItems(spinnerList: ArrayList<String>) {
        if (isfromsearch) {
            datalist = spinnerList
            recyclerAdapter =
                SpinnerRecyclerAdapter(context, datalist, object : OnItemSelectListener {
                    override fun setOnItemSelectListener(position: Int, selectedString: String) {
                        selectedItemPosition = position
                        selectedItem = selectedString
                        if (dismissSpinnerOnItemClick) dismiss()
                        if (::onItemSelectListener.isInitialized) onItemSelectListener.setOnItemSelectListener(
                            position,
                            selectedString
                        )
                    }
                })


        } else {
            recyclerAdapter =
                SpinnerRecyclerAdapter(context, spinnerList, object : OnItemSelectListener {
                    override fun setOnItemSelectListener(position: Int, selectedString: String) {
                        selectedItemPosition = position
                        selectedItem = selectedString
                        if (dismissSpinnerOnItemClick) dismiss()
                        if (::onItemSelectListener.isInitialized) onItemSelectListener.setOnItemSelectListener(
                            position,
                            selectedString
                        )
                    }
                })
        }

    }

    //Private helper methods
    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun dismissDialogOnDestroy() {
        if (getDialogInfo(false))
            dialog.dismiss()
    }

    private fun initLifeCycleObserver() {
        if (context is AppCompatActivity)
            context.lifecycle.addObserver(this)
        if (context is FragmentActivity)
            context.lifecycle.addObserver(this)
        if (context is Fragment)
            context.lifecycle.addObserver(this)
    }

    private fun getDialogInfo(toShow: Boolean): Boolean {
        return if (toShow) {
            !::dialog.isInitialized || !dialog.isShowing
        } else
            ::dialog.isInitialized && dialog.isShowing && dialog.window != null && dialog.window?.decorView != null && dialog.window?.decorView?.isAttachedToWindow!!
    }

    private fun AlertDialog.initView() {
        setOnShowListener {
            CircularReveal.startReveal(true, this, object : OnAnimationEnd {
                override fun onAnimationEndListener(isRevealed: Boolean) {
                    if (isRevealed) {
                        toggleKeyBoard(true)
                    }
                }
            }, animationDuration)
        }

        if (spinnerCancelable || negativeButtonVisibility != SpinnerView.VISIBLE)
            setOnCancelListener {
                if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(
                    null
                )
            }

        dialog.setOnKeyListener { _, keyCode, event ->
            if (event?.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                this.dismiss()
            }
            false
        }

        //init WindowTittle
        if (windowTitle != null || windowTitleVisibility.visibility == SearchView.VISIBLE) {
            textViewTitle = findViewById(R.id.textViewTitle)!!
            textViewTitle.visibility = View.VISIBLE
            textViewTitle.text = windowTitle
            textViewTitle.setTextColor(windowTitleTextColor)
        } else textViewTitle.visibility = windowTitleVisibility.visibility

        //init SearchView
        if (searchViewVisibility.visibility == SearchView.VISIBLE) {
            searchview.queryHint = searchQueryHint
            /* searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                 override fun onQueryTextSubmit(query: String?): Boolean {
                     if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(query)
                     return false
                 }

                 override fun onQueryTextChange(newText: String?): Boolean {
                     if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(newText)
                     return false
                 }

             })*/

            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (::recyclerAdapter.isInitialized) {
                        recyclerAdapter.filter(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Assuming you have a method to make API calls and update the spinner
                    if (!newText.isNullOrBlank()) {
                        handler.postDelayed({
                            // Call your API with newText as the search query
                            makeApiCallAndUpdateSpinner(newText)
                        }, 500)
                    }
                    return false
                }
            })
        } else searchview.visibility = searchViewVisibility.visibility


        //init NegativeButton
        if (negativeButtonVisibility.visibility == SearchView.VISIBLE) {
            buttonClose.setOnClickListener {
                it.isClickable = false
                this.dismiss()
            }
            buttonClose.text = negativeButtonText
            buttonClose.setTextColor(negativeButtonTextColor)
        } else buttonClose.visibility = negativeButtonVisibility.visibility

        //set Recycler Adapter
        if (::recyclerAdapter.isInitialized) {
            recyclerAdapter.highlightSelectedItem = highlightSelectedItem
            recyclerView.adapter = recyclerAdapter
        }
    }

    private fun initDialogColorScheme() {
        if (windowTopBackgroundColor != null)
            headLayouts.background = ColorDrawable(windowTopBackgroundColor!!)
        if (negativeButtonBackgroundColor != null)
            buttonClose.backgroundTintList =
                ColorStateList.valueOf(negativeButtonBackgroundColor!!)
    }

    private fun AlertDialog.initAlertDialogWindow() {
        val colorDrawable = ColorDrawable(Color.TRANSPARENT)
        val insetBackgroundDrawable = InsetDrawable(colorDrawable, 50, 40, 50, 40)
        window?.setBackgroundDrawable(insetBackgroundDrawable)
        window?.attributes?.layoutAnimationParameters
        window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun toggleKeyBoard(showKeyBoard: Boolean) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (showKeyboardByDefault && showKeyBoard) {
            searchview.post {
                searchview.requestFocus()
                imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1)
            }
        } else {
            imm?.toggleSoftInput(0, 0)
        }
    }

    fun makeApiCallAndUpdateSpinner(query: String) {

        var tokenmanager = TokenManager(context)
        var token = tokenmanager.getAccessToken()
        try {
            ApiUtils.getAPIService(context).SearchClientJobswithquery(
                token.toString(), global.clietidforBulkMsg!!.toInt(), query
            )
                .enqueue(object : Callback<SearchJobsClientsResp> {
                    override fun onResponse(
                        call: Call<SearchJobsClientsResp>,
                        response: Response<SearchJobsClientsResp>
                    ) {

                        if (response.body() != null) {
                            var itemslist: ArrayList<String> = ArrayList()
                            for (i in 0 until response.body()!!.data.size) {
                                itemslist.add(response.body()!!.data.get(i).positionName)
                            }

                            global.searchedJobData = response.body()
                            datalist = itemslist
                            isfromsearch = true
                            //    setSpinnerListItems(itemslist)
                            val adapter = SpinnerRecyclerAdapter(
                                context,
                                datalist,
                                object : OnItemSelectListener {
                                    override fun setOnItemSelectListener(
                                        position: Int,
                                        selectedString: String
                                    ) {
                                        selectedItemPosition = position
                                        selectedItem = selectedString
                                        if (dismissSpinnerOnItemClick) dismiss()
                                        if (::onItemSelectListener.isInitialized) onItemSelectListener.setOnItemSelectListener(
                                            position,
                                            selectedString
                                        )
                                    }
                                })
                            recyclerView.adapter = adapter
                            //updateRecyclerView(itemslist)
                        }
                    }

                    override fun onFailure(call: Call<SearchJobsClientsResp>, t: Throwable) {
                        Log.i("exceptionddsfdsfds", t.toString())

                    }
                })
        } catch (ex: java.lang.Exception) {
            Log.i("exceptionddsfdsfds", ex.toString())
        }
    }

}
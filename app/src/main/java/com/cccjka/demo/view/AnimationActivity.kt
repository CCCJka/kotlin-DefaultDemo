package com.cccjka.demo.view


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cccjka.demo.R
import com.cccjka.demo.adapter.LeftSideMenuAdapter
import com.cccjka.demo.databinding.ActivityAnimationBinding
import com.cccjka.demo.dialog.ShareDialog
import com.cccjka.demo.fragment.HistoryFragment
import com.cccjka.demo.fragment.HotFragment
import com.cccjka.demo.fragment.MainFragment
import com.cccjka.demo.fragment.PersonInfoFragment
import com.cccjka.demo.fragment.SettingFragment
import com.cccjka.demo.navigator.FragmentNavigator
import kotlinx.coroutines.delay

class AnimationActivity: AppCompatActivity(){

    private lateinit var viewBinding: ActivityAnimationBinding

    private var personInfoFragment: PersonInfoFragment? = PersonInfoFragment()
    private var settingFragment: SettingFragment? = SettingFragment()
    private var mainFragment: MainFragment? = MainFragment()
    private var hotFragment: HotFragment? = HotFragment()
    private var historyFragment: HistoryFragment? = HistoryFragment()
    private val fragmentList: ArrayList<Any?> = arrayListOf(mainFragment, hotFragment, historyFragment)

    val list = listOf("个人信息", "设置", "分享")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        if (savedInstanceState == null){
            initAll()
        }
    }

    private fun initAll(){
        initView()
        initData()
        viewBinding.btnToTop.setOnClickListener{
            mainFragment?.scrollToTop()
            hotFragment?.scrollToTop()
            historyFragment?.scrollToTop()
        }
    }

    private fun initView() {
        personInfoFragment = PersonInfoFragment()
        settingFragment = SettingFragment()
        mainFragment = MainFragment()
        hotFragment = HotFragment()
        historyFragment = HistoryFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.rl_fragment_container, mainFragment!!, "main")
            .hide(mainFragment!!)
            .add(R.id.rl_fragment_container, hotFragment!!, "hot")
            .hide(hotFragment!!)
            .add(R.id.rl_fragment_container, historyFragment!!, "history")
            .hide(historyFragment!!)
            .commit()
        setFragment(R.drawable.home_click)
//        replaceFragment(mainFragment!!)
    }

    private fun initData(){
        val leftSideMenuAdapter = LeftSideMenuAdapter(list)
        val linearLayout = LinearLayoutManager(this)
        viewBinding.rvItem.layoutManager = linearLayout
        viewBinding.rvItem.adapter =  leftSideMenuAdapter

        viewBinding.btnLogOut.setOnClickListener{
            logOut()
        }

        leftSideMenuAdapter.setOnItemClickListener(object : LeftSideMenuAdapter.clickListener{
            override fun onClick(clickItem: String) {
                when(clickItem){
                    "个人信息" -> personInfoFragment?.let { replaceFragment(it) }
                    "设置" -> settingFragment?.let { replaceFragment(it) }
                    "分享" -> showShareingDialog()
                }
                viewBinding.drawerlayout.close()
            }
        })
        viewBinding.ivMainFragment.setOnClickListener{
            setFragment(R.drawable.home_click)
//            mainFragment?.let { it1 -> replaceFragment(it1) }
        }
        viewBinding.ivHotFragment.setOnClickListener{
            setFragment(R.drawable.hot_click)
//            hotFragment?.let { it1 -> replaceFragment(it1) }
        }
        viewBinding.ivHistoryFragment.setOnClickListener{
            setFragment(R.drawable.history_click)
//            historyFragment?.let { it1 -> replaceFragment(it1) }
        }
    }

    private fun setFragment(drawableId: Int){
        when(drawableId){
            R.drawable.home_click -> {
                Glide.with(this).load(R.drawable.home_click).into(viewBinding.ivMainFragment)
                Glide.with(this).load(R.drawable.hotpoint).into(viewBinding.ivHotFragment)
                Glide.with(this).load(R.drawable.history).into(viewBinding.ivHistoryFragment)
                mainFragment?.let { changeFragment(it) }
            }
            R.drawable.hot_click -> {
                Glide.with(this).load(R.drawable.home).into(viewBinding.ivMainFragment)
                Glide.with(this).load(R.drawable.hot_click).into(viewBinding.ivHotFragment)
                Glide.with(this).load(R.drawable.history).into(viewBinding.ivHistoryFragment)
                hotFragment?.let { changeFragment(it) }
            }
            R.drawable.history_click -> {
                Glide.with(this).load(R.drawable.home).into(viewBinding.ivMainFragment)
                Glide.with(this).load(R.drawable.hotpoint).into(viewBinding.ivHotFragment)
                Glide.with(this).load(R.drawable.history_click).into(viewBinding.ivHistoryFragment)
                historyFragment?.let { changeFragment(it) }
            }
        }
    }

    private fun showShareingDialog() {
        val dialog = ShareDialog(this)
        dialog.show()
        clipShareContent()
    }

    //复制内容到系统粘贴板
    private fun clipShareContent(){
        val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ClipData.newPlainText("label", "https://jslrepository.us.kg")?.let { clip.setPrimaryClip(it) }
    }


    /**
    * replace函数会将fragment销毁，故不适用
    * */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_fade, R.anim.fragment_appear, R.anim.fragment_out)
            .replace(R.id.rl_fragment_container, fragment)
            .commit()
    }

    private fun changeFragment(fragment: Fragment){
        val tag = fragment.tag
        var code  = when(tag){
            "main" -> 1
            "hot" -> 2
            "history" -> 3
            else -> 0
        }
        if (code == 1){

        }
        supportFragmentManager.beginTransaction()
            .show(fragment)
            .hide(hotFragment!!)
            .hide(historyFragment!!)
            .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_fade, R.anim.fragment_appear, R.anim.fragment_out)
            .commit()
    }

    private fun logOut(){
        Toast.makeText(this, "log out", LENGTH_LONG).show()
    }

    private fun fragmentDestroy() {
        personInfoFragment?.onDestroy()
        personInfoFragment = null
        settingFragment?.onDestroy()
        hotFragment = null
        mainFragment?.onDestroy()
        mainFragment = null
        hotFragment?.onDestroy()
        hotFragment = null
        historyFragment?.onDestroy()
        historyFragment = null
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentDestroy()
    }
}

package com.cccjka.demo.view


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cccjka.demo.R
import com.cccjka.demo.adapter.LeftSideMenuAdapter
import com.cccjka.demo.application.MyApplication
import com.cccjka.demo.databinding.ActivityAnimationBinding
import com.cccjka.demo.dialog.ShareDialog
import com.cccjka.demo.fragment.HistoryFragment
import com.cccjka.demo.fragment.HotFragment
import com.cccjka.demo.fragment.MainFragment
import com.cccjka.demo.utils.CommonUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class AnimationActivity: AppCompatActivity(){

    private lateinit var viewBinding: ActivityAnimationBinding

    private var mainFragment: MainFragment? = null
    private var hotFragment: HotFragment? = null
    private var historyFragment: HistoryFragment? = null

    val list = listOf("个人信息", "设置", "分享")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initAll()
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
                    "个人信息" -> {
                        CommonUtils.navigation(application, PersonInfoActivity::class.java)
                        mainFragment?.pauseVideo()
                        hotFragment?.pauseVideo()
                    }
                    "设置" -> {
                        CommonUtils.navigation(application, SettingActivity::class.java)
                        mainFragment?.pauseVideo()
                        hotFragment?.pauseVideo()
                    }
                    "分享" -> showShareingDialog()
                }
                viewBinding.drawerlayout.close()
            }
        })
        viewBinding.ivMainFragment.setOnClickListener{
            setFragment(R.drawable.home_click)
        }
        viewBinding.ivHotFragment.setOnClickListener{
            setFragment(R.drawable.hot_click)

        }
        viewBinding.ivHistoryFragment.setOnClickListener{
            setFragment(R.drawable.history_click)
        }
    }

    private fun setFragment(drawableId: Int){
        clearVideo()
        when(drawableId){
            R.drawable.home_click -> {
                Glide.with(MyApplication.context).load(R.drawable.home_click).into(viewBinding.ivMainFragment)
                Glide.with(MyApplication.context).load(R.drawable.hotpoint).into(viewBinding.ivHotFragment)
                Glide.with(MyApplication.context).load(R.drawable.history).into(viewBinding.ivHistoryFragment)
                changeFragment(mainFragment!!)
            }
            R.drawable.hot_click -> {
                Glide.with(MyApplication.context).load(R.drawable.home).into(viewBinding.ivMainFragment)
                Glide.with(MyApplication.context).load(R.drawable.hot_click).into(viewBinding.ivHotFragment)
                Glide.with(MyApplication.context).load(R.drawable.history).into(viewBinding.ivHistoryFragment)
                changeFragment(hotFragment!!)
            }
            R.drawable.history_click -> {
                Glide.with(MyApplication.context).load(R.drawable.home).into(viewBinding.ivMainFragment)
                Glide.with(MyApplication.context).load(R.drawable.hotpoint).into(viewBinding.ivHotFragment)
                Glide.with(MyApplication.context).load(R.drawable.history_click).into(viewBinding.ivHistoryFragment)
                changeFragment(historyFragment!!)
            }
        }
    }

    private fun clearVideo(){
        mainFragment?.releaseVideo()
        hotFragment?.releaseVideo()
        historyFragment?.releaseVideo()
    }

    private fun showShareingDialog() {
        val dialog = ShareDialog(this)
        dialog.show()
        clipShareContent()
    }

    //复制内容到系统粘贴板
    private fun clipShareContent(){
        val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ClipData.newPlainText("label", "")?.let { clip.setPrimaryClip(it) }
    }

    private fun changeFragment(fragment: Fragment){
        val tag = fragment.tag
        val code  = when(tag){
            "main" -> 1
            "hot" -> 2
            "history" -> 3
            else -> 0
        }
        when(code){
            1 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_fade, R.anim.fragment_appear, R.anim.fragment_out)
                .show(mainFragment!!)
                .hide(hotFragment!!)
                .hide(historyFragment!!)
                .commit()
            2 -> supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_fade, R.anim.fragment_appear, R.anim.fragment_out)
                .show(hotFragment!!)
                .hide(mainFragment!!)
                .hide(historyFragment!!)
                .commit()
            3 -> {
                supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_fade, R.anim.fragment_appear, R.anim.fragment_out)
                .show(historyFragment!!)
                .hide(hotFragment!!)
                .hide(mainFragment!!)
                .commit()
                historyFragment?.reloadAdapter()
            }
            else -> Log.e("code Error", "")
        }
    }

    private fun logOut(){
        Toast.makeText(this, "log out", LENGTH_LONG).show()
    }

    private fun clearGlideCache(){
        Glide.with(MyApplication.context).clear(viewBinding.ivMainFragment)
        Glide.with(MyApplication.context).clear(viewBinding.ivHotFragment)
        Glide.with(MyApplication.context).clear(viewBinding.ivHistoryFragment)
    }

    private fun fragmentDestroy(){
        mainFragment?.onDestroy()
        mainFragment = null
        hotFragment?.onDestroy()
        hotFragment = null
        historyFragment?.onDestroy()
        historyFragment = null
    }

    override fun onResume() {
        super.onResume()
        if (mainFragment != null && mainFragment?.currentState() == StandardGSYVideoPlayer.CURRENT_STATE_PAUSE){
            mainFragment?.resumeVideo()
        }
        if (hotFragment != null && hotFragment?.currentState() == StandardGSYVideoPlayer.CURRENT_STATE_PAUSE){
            hotFragment?.resumeVideo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearGlideCache()
        fragmentDestroy()
    }
}

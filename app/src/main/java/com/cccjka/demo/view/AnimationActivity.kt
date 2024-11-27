package com.cccjka.demo.view


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cccjka.demo.R
import com.cccjka.demo.adapter.LeftSideMenuAdapter
import com.cccjka.demo.dialog.ShareDialog
import com.cccjka.demo.fragment.MainFragment
import com.cccjka.demo.fragment.PersonInfoFragment
import com.cccjka.demo.fragment.SettingFragment
import com.cccjka.demo.utils.CommonUtils
import com.cccjka.demo.viewmodel.AnimationViewModel

class AnimationActivity: AppCompatActivity(){

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var rv_item: RecyclerView
    private lateinit var btn_log_out: Button

    private lateinit var iv_main_fragment: ImageView

    private lateinit var viewmodel: AnimationViewModel

    private lateinit var personInfoFragment: PersonInfoFragment
    private lateinit var settingFragment: SettingFragment
    private lateinit var mainFragment: MainFragment

    val list = listOf("个人信息", "设置", "分享")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        initAll()
    }

    private fun initAll(){
        initView()
        initData()
    }

    private fun initView() {
        drawerLayout = findViewById(R.id.drawerlayout)
        btn_log_out = findViewById(R.id.btn_log_out)
        rv_item = findViewById(R.id.rv_item)
        iv_main_fragment = findViewById(R.id.iv_main_fragment)
        personInfoFragment = PersonInfoFragment()
        settingFragment = SettingFragment()
        mainFragment = MainFragment()
        replaceFragment(mainFragment)
    }

    private fun initData(){
        viewmodel = AnimationViewModel()
        val leftSideMenuAdapter = LeftSideMenuAdapter(list)
        val linearLayout = LinearLayoutManager(this)
        rv_item.layoutManager = linearLayout
        rv_item.adapter =  leftSideMenuAdapter

        btn_log_out.setOnClickListener{
            logOut()
        }

        leftSideMenuAdapter.setOnItemClickListener(object : LeftSideMenuAdapter.clickListener{
            override fun onClick(clickItem: String) {
                when(clickItem){
                    "个人信息" -> replaceFragment(personInfoFragment)
                    "设置" -> replaceFragment(settingFragment)
                    "分享" -> showShareingDialog()
                }
                drawerLayout.close()
            }
        })

        iv_main_fragment.setOnClickListener{
            replaceFragment(mainFragment)
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


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rl_fragment_container, fragment)
            .commit()
    }

    private fun logOut(){
        Toast.makeText(this, "log out", LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

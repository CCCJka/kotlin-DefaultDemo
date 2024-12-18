package com.cccjka.demo.view


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cccjka.demo.R
import com.cccjka.demo.adapter.LeftSideMenuAdapter
import com.cccjka.demo.databinding.ActivityAnimationBinding
import com.cccjka.demo.dialog.ShareDialog
import com.cccjka.demo.fragment.MainFragment
import com.cccjka.demo.fragment.PersonInfoFragment
import com.cccjka.demo.fragment.SettingFragment
import com.cccjka.demo.viewmodel.AnimationViewModel

class AnimationActivity: AppCompatActivity(){

    private lateinit var viewBinding: ActivityAnimationBinding

    private lateinit var viewmodel: AnimationViewModel

    private lateinit var personInfoFragment: PersonInfoFragment
    private lateinit var settingFragment: SettingFragment
    private lateinit var mainFragment: MainFragment

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
    }

    private fun initView() {
        personInfoFragment = PersonInfoFragment()
        settingFragment = SettingFragment()
        mainFragment = MainFragment()
        replaceFragment(mainFragment)
    }

    private fun initData(){
        viewmodel = AnimationViewModel()
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
                    "个人信息" -> replaceFragment(personInfoFragment)
                    "设置" -> replaceFragment(settingFragment)
                    "分享" -> showShareingDialog()
                }
                viewBinding.drawerlayout.close()
            }
        })
        viewBinding.ivMainFragment.setOnClickListener{
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

package com.example.familysafetyapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_CONTACTS
    )

    val permissionCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askingPermissions()

        val bottomnavbar = findViewById<BottomNavigationView>(R.id.bottomnavbar)

        bottomnavbar.setOnItemReselectedListener {menuItem ->

            if(menuItem.itemId == R.id.btnGuard){
                inflateFragment(Guard_Fragment.newInstance())
            }
            else if(menuItem.itemId == R.id.btnHome){
                inflateFragment(home_Fragment.newInstance())
            }
            else if(menuItem.itemId == R.id.btnDashboard){
                inflateFragment(MapsFragment())
            }
            else if(menuItem.itemId == R.id.btnProfile){
                inflateFragment(profile_Fragment.newInstance())
            }

            true
        }

        bottomnavbar.selectedItemId = R.id.btnHome
    }


    private fun askingPermissions(){
        ActivityCompat.requestPermissions(this, permissions, permissionCode)
    }

    private fun inflateFragment(newInstance : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newInstance) //replace initial container to fragment.
        transaction.commit() //execute fragment
    }


    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == permissionCode){

            if(checkPermissionsGranted()){
                openCamera()
            }
            else{

            }
        }
    }

    private fun checkPermissionsGranted() : Boolean {
        for (item in permissions) {
            if(ContextCompat.checkSelfPermission(this, item) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }*/
}
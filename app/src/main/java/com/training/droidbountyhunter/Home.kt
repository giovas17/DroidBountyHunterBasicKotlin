package com.training.droidbountyhunter

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.training.fragments.AcercaDeFragment
import com.training.fragments.ListFragment
import com.training.fragments.SECTION_NUMBER
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        tabs.setupWithViewPager(container)

        fab.setOnClickListener { view ->
            val intent = Intent(this, AgregarActivity::class.java)
            startActivityForResult(intent, 0)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_agregar) {
            val intent = Intent(this, AgregarActivity::class.java)
            startActivityForResult(intent,0)
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            if (fragments.size < 3){ // Si no contiene los 3 fragments los agregará
                if (position < 2){
                    fragments.add(position, ListFragment())
                    val arguments = Bundle()
                    arguments.putInt(SECTION_NUMBER, position)
                    fragments[position].arguments = arguments
                }else{
                    fragments.add(position, AcercaDeFragment())
                }
            }
            return fragments[position]
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }

        override fun getPageTitle(position: Int) = when (position) {
            0 -> getString(R.string.titulo_fugitivos).toUpperCase()
            1 -> getString(R.string.titulo_capturados).toUpperCase()
            else -> getString(R.string.titulo_acerca_de).toUpperCase()
        }
    }

}

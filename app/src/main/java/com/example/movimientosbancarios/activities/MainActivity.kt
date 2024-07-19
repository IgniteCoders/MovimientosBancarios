package com.example.movimientosbancarios.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movimientosbancarios.R
import com.example.movimientosbancarios.adapter.MovementsAdapter
import com.example.movimientosbancarios.data.Movement
import com.example.movimientosbancarios.data.MovementDAO
import com.example.movimientosbancarios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MovementsAdapter

    lateinit var movementDAO: MovementDAO

    var movementList: List<Movement> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movementDAO = MovementDAO(this)

        initView()
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_movement -> {
                addMovement()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addMovement() {
        val intent = Intent(this, NewMovementActivity::class.java)
        startActivity(intent)
    }

    private fun initView() {
        /*binding.addTaskButton.setOnClickListener {
            addTask()
        }*/

        adapter = MovementsAdapter(listOf(), {
            onItemClickListener(it)
        }, {})

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //configureGestures()
    }

    private fun loadData() {
        movementList = movementDAO.findAll().toMutableList()
        adapter.updateItems(movementList)

        val balance = movementList.sumOf { it.quantity.toBigDecimal() }.toFloat()
        binding.balanceTextView.text = "$balance €"
        if (balance < 0.0) {
            binding.balanceTextView.setTextColor(getColor(R.color.negative))
        } else {
            binding.balanceTextView.setTextColor(getColor(R.color.black))
        }
    }

    private fun onItemClickListener(position: Int) {
        TODO("Not yet implemented")
    }
}
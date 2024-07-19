package com.example.movimientosbancarios.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movimientosbancarios.R
import com.example.movimientosbancarios.data.Movement
import com.example.movimientosbancarios.databinding.ItemMovementBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class MovementsAdapter (private var items: List<Movement> = listOf(),
                        private val onClickListener: (position:Int) -> Unit,
                        private val onRemoveListener: (position:Int) -> Unit
) : RecyclerView.Adapter<MovementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val binding = ItemMovementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovementViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        holder.render(items[position])
        holder.itemView.setOnClickListener { onClickListener(holder.adapterPosition) }
        /*holder.binding.doneCheckBox.setOnCheckedChangeListener { checkbox, isChecked ->
            if (checkbox.isPressed) {
                onCheckedListener(holder.adapterPosition)
            }
        }
        holder.binding.deleteButton.setOnClickListener {
            onRemoveListener(holder.adapterPosition)
        }*/
    }

    fun updateItems(results: List<Movement>) {
        /*val diffUtils = MovementDiffUtils(items, results)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        items = results
        diffResult.dispatchUpdatesTo(this)*/

        items = results
        notifyDataSetChanged()
    }
}

class MovementViewHolder(val binding: ItemMovementBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        var formatter: SimpleDateFormat = SimpleDateFormat("dd/MMMM/yyyy")
    }

    fun render(movement: Movement) {
        binding.quantityTextView.text = "${movement.quantity} €"

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = movement.date
        binding.dateTextView.text = formatter.format(calendar.time)

        if (movement.quantity < 0.0) {
            binding.quantityTextView.setTextColor(itemView.context.getColor(R.color.negative))
        } else {
            binding.quantityTextView.setTextColor(itemView.context.getColor(R.color.positive))
        }
    }

}
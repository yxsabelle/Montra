
package com.example.montraproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Slide(val image: Int, val title: String, val desc: String)

class OnboardingAdapter(private val slides: List<Slide>) :
    RecyclerView.Adapter<OnboardingAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgSlide)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val desc: TextView = view.findViewById(R.id.tvDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = slides[position]
        holder.img.setImageResource(s.image)
        holder.title.text = s.title
        holder.desc.text = s.desc
    }

    override fun getItemCount() = slides.size
}

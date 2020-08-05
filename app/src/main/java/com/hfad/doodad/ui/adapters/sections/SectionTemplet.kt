package com.hfad.doodad.ui.adapters.sections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter
import com.cruxlab.sectionedrecyclerview.lib.SimpleSectionAdapter
import com.hfad.doodad.R

class SectionTemplet( val category : String?, val onCLick: () -> Unit ) : SimpleSectionAdapter<SectionTemplet.AllTaskViewHolder>()
{
    private val list = listOf("All Tasks")
    inner class AllTaskViewHolder(view: View) : BaseSectionAdapter.ItemViewHolder(view)
    {
        private var categoryLabel : TextView = view.findViewById(R.id.category_label)
        private var countLabel : TextView = view.findViewById(R.id.task_count)
        private var addCategoryDrawable : ImageView = view.findViewById(R.id.add_category)
        private var deleteCategoryDrawable : ImageView = view.findViewById(R.id.delete_category)
        init {
            category?.let {
                categoryLabel.visibility = View.VISIBLE
                countLabel.visibility = View.VISIBLE
                categoryLabel.text = it
                view.setOnClickListener{
                    onCLick.invoke()
                }
            } ?: run{
                addCategoryDrawable.visibility = View.VISIBLE
                view.setOnClickListener{
                    onCLick.invoke()
                }

            }
        }
        fun bind(){
           countLabel.text = "0 ITEMS"
        }
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, type: Short): AllTaskViewHolder {
        return AllTaskViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.section_items, parent, false))
    }

    override fun onBindItemViewHolder(holder: AllTaskViewHolder?, position: Int) {
        holder?.bind()
    }

    override fun getItemCount(): Int = list.size
}
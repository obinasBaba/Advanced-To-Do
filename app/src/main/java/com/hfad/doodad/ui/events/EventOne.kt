package com.hfad.doodad.ui.events

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter
import com.cruxlab.sectionedrecyclerview.lib.SectionAdapter
import com.cruxlab.sectionedrecyclerview.lib.SectionDataManager
import com.cruxlab.sectionedrecyclerview.lib.SectionItemSwipeCallback
import com.hfad.doodad.R
import kotlinx.android.synthetic.main.fragment_event_one.*


class EventOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_one, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_id.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val section :SectionDataManager = SectionDataManager()
        section.swipeCallback
        rv_id.adapter = section.adapter

        section_header_layout.attachTo(rv_id, section)


        section.addSection( MyAdapter(show = true, pin = true).apply {
            updateHeaderPinnedState(true)
        },  SwipeCallBack(), 1)
        section.addSection( MyAdapter2(show = true, pin = true).apply { updateHeaderPinnedState(true)}, 2 )
        section.addSection( MyAdapter2(show = true, pin = true).apply { updateHeaderPinnedState(true)}, 3 )
    }


}

class SwipeCallBack() : SectionItemSwipeCallback()
{
    private val background: ColorDrawable = ColorDrawable()

    override fun onSwiped(p0: BaseSectionAdapter.ItemViewHolder?, p1: Int) {
        Toast.makeText(p0?.itemView?.context, "${p0?.sectionAdapterPosition}", Toast.LENGTH_SHORT).show()
    }

    override fun getSwipeDirFlags(p0: RecyclerView?, p1: BaseSectionAdapter.ItemViewHolder?): Int {
        return ItemTouchHelper.LEFT
    }

    override fun onChildDraw(
        c: Canvas?, recyclerView: RecyclerView?, viewHolder: BaseSectionAdapter.ItemViewHolder?,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        // Draw something on canvas

        // Draw something on canvas
        val itemView = viewHolder!!.itemView
        background.color = Color.RED
        background.setBounds(
            (itemView.right + dX).toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        
    }
}

class MyAdapter(show: Boolean, pin: Boolean) : SectionAdapter< MyAdapter.ViewHolder,MyAdapter.HViewHolder  >(show, pin)
{
    inner class HViewHolder(view: View): BaseSectionAdapter.HeaderViewHolder(view){
        val text: TextView
        init {
            text = view.findViewById(R.id.header)
        }
        fun bind(){
            text.text = "blblblblblblbllb"
        }
    }

    val list = listOf("one", "two", "three","one", "two", "three","one", "two", "three","one"
        )

    inner class ViewHolder(view: View) : BaseSectionAdapter.ItemViewHolder(view)
    {
        var textView : TextView

        init {

            textView = view.findViewById(R.id.item)
        }

        fun bind( string: String ){
            textView.text = string
        }
    }

    override fun onCreateItemViewHolder(p0: ViewGroup?, p1: Short): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0?.context).inflate(R.layout.catagory_lable, p0, false))
    }

    override fun onBindItemViewHolder(p0: ViewHolder?, p1: Int) {
         p0?.bind(list[p1])
    }

    override fun getItemCount(): Int  = list.size
    override fun onCreateHeaderViewHolder(p0: ViewGroup?): HViewHolder {
        return HViewHolder(LayoutInflater.from(p0?.context).inflate(R.layout.section_row, p0, false))
    }

    override fun onBindHeaderViewHolder(p0: HViewHolder?) {
        p0?.bind()
    }

}

class MyAdapter2( show: Boolean, pin: Boolean) :
    SectionAdapter< MyAdapter2.ViewHolder2, MyAdapter2.HeaderViewHolder>( show,pin )
{
    val list = listOf("o", "a", "b","o", "a", "b","o",  "b","o", "a", "b","o", "a", "b")

    inner class HeaderViewHolder(view: View): BaseSectionAdapter.HeaderViewHolder(view){
        val text : TextView
        init {
            text = view.findViewById(R.id.header)
        }

        fun bind(){
            text.text = ";aklsdjf;alkdsfj;alksdfj"
        }
    }

    inner class ViewHolder2(view: View) : BaseSectionAdapter.ItemViewHolder(view)
    {
        var textView : TextView

        init {

            textView = view.findViewById(R.id.item)
        }

        fun bind( string: String ){
            textView.text = string
        }
    }

    override fun onCreateItemViewHolder(p0: ViewGroup?, p1: Short): ViewHolder2 {
        return ViewHolder2(LayoutInflater.from(p0?.context).inflate(R.layout.selection_item_two, p0, false))
    }

    override fun onBindItemViewHolder(p0: ViewHolder2?, p1: Int) {
        p0?.bind(list[p1])
    }

    override fun getItemCount(): Int  = list.size


    override fun onCreateHeaderViewHolder(p0: ViewGroup?): HeaderViewHolder {
        return HeaderViewHolder(LayoutInflater.from(p0?.context).inflate(R.layout.section_row, p0, false))
    }

    override fun onBindHeaderViewHolder(p0: HeaderViewHolder?) {
        p0?.bind()
    }


}
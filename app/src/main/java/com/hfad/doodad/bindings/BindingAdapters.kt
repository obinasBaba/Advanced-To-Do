package com.hfad.doodad.bindings

import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.hfad.doodad.dataLayer.database.Task

@BindingAdapter("android::show")
fun showLoading( view: ProgressBar, boolean: Boolean ){

}

@BindingAdapter("android:onTitleChange")
fun onTitleChange( view: EditText, title: MutableLiveData<String> ){
    view.doAfterTextChanged{ text -> title.value = text.toString() }
}

@BindingAdapter("android:onBodyChange")
fun onBodyChange( view: EditText, body: MutableLiveData<String>){
    view.doAfterTextChanged{ text -> body.value = text.toString() }
}
package com.tcf.tcfspeedtester.utils.error_handle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tcf.tcfspeedtester.R
import com.tcf.tcfspeedtester.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {

    private lateinit var binding:ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val throwable = intent.getSerializableExtra(EXTRA_THROWABLE) as? Throwable
        throwable?.let {
            // Display error details in your activity UI
            binding.errorDetailsTextView.text = throwable.toString()
        }
    }

    companion object {
        private const val EXTRA_THROWABLE = "extra_throwable"

        fun newIntent(context: Context, throwable: Throwable): Intent {
            return Intent(context, ErrorActivity::class.java).apply {
                putExtra(EXTRA_THROWABLE, throwable)
            }
        }
    }
}
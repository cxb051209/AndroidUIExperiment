package cse.fjnu.androiduiexperiment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        Button btn = findViewById(R.id.btnShowDialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Sign in", (dialog, which) -> {
            EditText etUser = dialogView.findViewById(R.id.etUsername);
            EditText etPass = dialogView.findViewById(R.id.etPassword);
            Toast.makeText(this,
                    "用户名: " + etUser.getText() + "\n密码: " + etPass.getText(),
                    Toast.LENGTH_SHORT).show();
        });

        builder.create().show();
    }
}
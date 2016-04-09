package fitnessapp.supinfo.fitnessapp.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import fitnessapp.supinfo.fitnessapp.MainActivity;
import fitnessapp.supinfo.fitnessapp.WeightFragment;
import fitnessapp.supinfo.fitnessapp.model.Runner;

public class RunnerListTextviewListener implements View.OnClickListener {

    private static Activity activity;
    private int id;
    private Runner runner;


    public RunnerListTextviewListener(int id, Runner runner) {
        this.id = id;
        this.runner = runner;
    }

    public static void setActivity(Activity a) {
        activity = a;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("id", this.id);
        intent.putExtra("runner", this.runner);
        this.activity.startActivityForResult(intent, MainActivity.RUNNER_ACTIVITY_CODE);


    }
}
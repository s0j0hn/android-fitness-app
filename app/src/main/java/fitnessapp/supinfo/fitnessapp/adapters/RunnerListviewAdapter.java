package fitnessapp.supinfo.fitnessapp.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fitnessapp.supinfo.fitnessapp.listeners.RunnerListTextviewListener;
import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.List;

public class RunnerListviewAdapter extends BaseAdapter {
    private List<Runner> runners;
    private Context context;

    public RunnerListviewAdapter(List<Runner> runners, Context context) {
        this.runners = runners;
        this.context = context;
    }

    public int getCount() {
        return runners.size();
    }

    public Runner getItem(int position) {
        return runners.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        TextView text = new TextView(this.context);
        Runner item = getItem(position);

        RunnerListTextviewListener runnerListerner = new RunnerListTextviewListener(position, item);
        text.setOnClickListener(runnerListerner);

        text.setPadding(15, 15, 15, 15);
        text.setText(item.getWeight() +" KG  le jour "+ DateFormat.format("yyyy-MM-dd ", item.getDate()));

        text.setTextSize(20);

        return text;
    }

}

package fitnessapp.supinfo.fitnessapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.List;

public class RunnerListAdapter extends BaseAdapter {

    private List<Runner> runners;
    private Context context;

    public RunnerListAdapter(List<Runner> runners, Context context) {
        this.runners = runners;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

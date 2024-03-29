package ebozkurt.listtodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;


public class TaskFragment extends Fragment {

    private static final String TAG = "TaskFragment";
    private static final String ARG_TASK_ID = "task_id";

    private Task mTask;
    private EditText mTitleField;
    private EditText mDescriptionField;
    private CheckBox mDoneCheckBox;
    private SeekBar mPrioritySeekBar;


    public static TaskFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        mTask = TaskLab.get(getActivity()).getTask(taskId);
        //  setHasOptionsMenu(true); //maybe later needed for navigation up button


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);
        mTitleField = (EditText) v.findViewById(R.id.fragment_task_title_textview);
        mTitleField.setText(mTask.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "title text changed to " + charSequence.toString());
                mTask.setTitle(charSequence.toString());
                /*
                if(charSequence.length() == 0){
                    mTitleField.setError(getResources().getString(R.string.task_title_empty));
                }
*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDescriptionField = (EditText) v.findViewById(R.id.fragment_task_description_textview);
        mDescriptionField.setText(mTask.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "description text changed to " + charSequence.toString());
                mTask.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDoneCheckBox = (CheckBox) v.findViewById(R.id.fragment_task_done_checkbox);
        mDoneCheckBox.setChecked(mTask.isDone());
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTask.setDone(b);
            }
        });

        mPrioritySeekBar = (SeekBar) v.findViewById(R.id.fragment_task_priority_seekbar);
        mPrioritySeekBar.setProgress(mTask.getPriority());
        mPrioritySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i(TAG, "seekbar priority progress changed to " + i);
                mTask.setPriority(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return v;
    }

/*
    @Override
    public void onPause() {
        if (mTask.getTitle() == null) {
            Toast.makeText(getContext(), R.string.task_title_rename, Toast.LENGTH_SHORT).show();
            TaskLab taskLab = TaskLab.get(getActivity());
            List<Task> tasks = taskLab.getTasks();
            tasks.remove(mTask);
//todo add this parts into back button of fragment, if it stays this way than will break app in case of press button of add new task,
// go to other app, return back, than fill parts and try to create a null task
            super.onPause();
        }
    }*/
}

package pvtitov.ytranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Павел on 06.06.2017.
 */

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();

        return view;
    }

    private void updateUI() {
        List<Word> words = WordLab.getOrCreateWordLab(getActivity()).getListOfWords();
        if (listAdapter == null) {
            listAdapter = new ListAdapter(words);
            recyclerView.setAdapter(listAdapter);
        } else {
            listAdapter.setWords(words);
            listAdapter.notifyDataSetChanged();
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Word word;
        private TextView wordTextView;
        private TextView translationTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            wordTextView = (TextView) itemView.findViewById(R.id.list_item_text_word);
            translationTextView = (TextView) itemView.findViewById(R.id.list_item_text_translation);
        }

        public void bindDataToItem(Word w){
            word = w;
            wordTextView.setText(word.getWord());
            translationTextView.setText(word.getTranslation());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MainActivity.createIntent(getActivity(), word.getWord());
            startActivity(intent);
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ItemHolder>{

        private List<Word> list;

        public ListAdapter (List<Word> list){
            this.list = list;
        }


        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Word item = list.get(position);
            holder.bindDataToItem(item);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setWords(List<Word> words) {
            this.list = words;
        }
    }
}

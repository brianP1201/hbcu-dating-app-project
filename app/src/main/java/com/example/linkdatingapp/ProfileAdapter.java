package com.example.linkdatingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    private List<ParseUser> profiles;
    private String[] colleges;

    public ProfileAdapter(Context context, List<ParseUser> profiles) {
        this.context = context;
        this.profiles = profiles;
        colleges = context.getResources().getStringArray(R.array.colleges_array);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseUser profile = profiles.get(position);
        holder.bind(profile);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername, tvDescription, tvAge;
        private ImageView ivProfileImage, ivCollegeLogo;

        public int findCollegeLogo(ParseUser user){
            int collegeLogo = 0;
            int position;

            // Array of College logos... Compared to array of College Strings to find correct logo
            int[] logoArray = new int[10];
            logoArray[0] = R.drawable.alabama_state_university_seal_svg;
            logoArray[1] = R.drawable.bowie_state_university_svg;
            logoArray[2] = R.drawable.hampton_university_seal;
            logoArray[3] = R.drawable.howard_university_seal_svg;
            logoArray[4] = R.drawable.morehouse_college_seal_svg;
            logoArray[5] = R.drawable.morgan_state_university_logo_svg;
            logoArray[6] = R.drawable.norfork_state_spartans_logo_svg;
            logoArray[7] = R.drawable.north_carolina_a_t_aggies_logo_svg;
            logoArray[8] = R.drawable.north_carolina_central_university_seal_svg;
            logoArray[9] = R.drawable.tennessee_state_university_seal_svg;

            for(int i = 0; i < 10; i++){
                if(colleges[i].equals(user.get("college").toString())){ // Find the correct index for the college
                    collegeLogo = logoArray[i]; // Assign the correct logo reference
                }
            }
            return collegeLogo;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivCollegeLogo = itemView.findViewById(R.id.ivCollegeLogo);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvAge = itemView.findViewById(R.id.tvAge);
        }

        public void bind(ParseUser user) {
            String age = "Age: " + String.valueOf(user.get("age"));
            // Bind the post data to the view elements
            tvUsername.setText(user.getUsername());
            tvDescription.setText(user.get("description").toString());
            tvAge.setText(age);
            ivCollegeLogo.setImageResource(findCollegeLogo(user)); // set the ImageView to the correct logo
            ParseFile image = user.getParseFile("profilePhoto");
            if(image != null) {
                Glide.with(context).load(user.getParseFile("profilePhoto").getUrl()).into(ivProfileImage);
            }
        }
    }
}

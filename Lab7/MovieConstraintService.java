package com.example.Lab7_Springboot;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MovieConstraintService {
    private boolean isRelated(Movie m1, Movie m2) {
        if (m1.getActors() == null || m2.getActors() == null) {
            return false;
        }

        // Check if the two movies share any actor by comparing actor IDs
        for (Actor actor1 : m1.getActors()) {
            for (Actor actor2 : m2.getActors()) {
                if (actor1.getId().equals(actor2.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
    public List<Movie> findUnrelatedMovies(int min, List<Movie> movies){
        int n = movies.size();
        if(n == 0){
            return new ArrayList<>();
        }
        Model model = new Model("Unrelated Movies Finder");
        BoolVar[] selected = model.boolVarArray("selected_movies", n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isRelated(movies.get(i), movies.get(j))) {
                    model.arithm(selected[i], "+", selected[j], "<=", 1).post();
                }
            }
        }
        IntVar sum = model.intVar("total_selected", 0, n);
        model.sum(selected, "=", sum).post();
        model.arithm(sum, ">", min).post();
        Solution solution = model.getSolver().findSolution();
        List<Movie> result = new ArrayList<>();
        if(solution != null){
            for(int i = 0; i < n; i++){
                if(selected[i].getValue() == 1){
                    result.add(movies.get(i));
                }
            }
        }
        return result;
    }
}

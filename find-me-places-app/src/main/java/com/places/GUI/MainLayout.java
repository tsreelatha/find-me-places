package com.places.GUI;
import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class MainLayout extends GridPane {

    private final SearchForm searchForm;

    private final SearchResult searchResult;

    @Autowired
    public MainLayout(SearchForm searchForm, SearchResult searchResult) {

        this.searchForm = searchForm;
        this.searchResult = searchResult;

        initComponent();
    }

    private void initComponent() {

        add(this.searchForm, 0, 0);
        add(this.searchResult, 0, 1);
    }
}

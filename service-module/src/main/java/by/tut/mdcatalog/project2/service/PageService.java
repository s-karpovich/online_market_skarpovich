package by.tut.mdcatalog.project2.service;

public interface PageService {
        int getPages(int countOfObjects);

        int getValidPage(Integer page, int countOfPages);

        int getOffset(int page);
    }
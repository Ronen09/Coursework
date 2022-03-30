import javafx.event.ActionEvent;

/**
 *  Represents all the pages that will be displayed.
 */
public class PageArray {
    private static String[] PageArray = {"MapTiling", "StatsPage", "CreateListing"};
    private static int currentPage = -1;


    /**
     * Returns the next page.
     * @return Next page.
     */
    public String returnNextPage(){
        currentPage = calculateWrappage(currentPage + 1);
        return PageArray[currentPage];
    }

    /**
     * Returns the last page.
     * @return last page.
     */
    public String returnLastPage(){
        currentPage = calculateWrappage(currentPage - 1);
        return PageArray[currentPage];
    }

    /**
     * Grab value of currentpage.
     * @return Ditto.
     */
    public int getCurrentPage(){
        return currentPage;
    }

    /**
     * Calculates wraparound.
     */
    private int calculateWrappage(int x){
        return (PageArray.length + x) % PageArray.length;
    }
}

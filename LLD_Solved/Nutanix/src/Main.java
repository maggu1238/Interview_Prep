//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

Book{
    int id;
    int copies;

    isAvailable(){
        if(copies > 0){
            return true;
        }
    }

    }

BookCopy extends Book(){
    User;

    issueDate;
    checkoutDate;
    BookCopy(id){
        super()
    }
}

interface command{
    execute();
}

addBookCommand implements command
{
    AddBookCommand(AdminManger)


    addBook(bookId)(
    if(catalogue.get(bookId)!=null){
        book =  catalogue.get(bookId);

    }
    else{
        BookCopy book = new BookCopy(id);
    }


    catalogue.put()
    )
}


AdminManager{

    map<string, book> catalogue;
    map<string, User> members;
    map<>
    AdminManager(){

    }



    removeBook()
    registerUser()
    removeUser()

    synchronized issueBook(string userId, String Bookid){
       user =  members.getUser(userId);
       user.BookList.add(catalogue.get(bookId));
    }

    synchronized returnBook(string userId, String BookId){
        user =  members.getUser(userId);
        user.BookList.remove(catalogue.get(bookId));
    }

    searchBooks(

    )

    trackBooks(
        iterate over the books
            if(date is outdated)
                user.notify();
    )

}

CreateUserFactory(){
    Use
}

interface user
{
    searchBooks();
}

RegisteredUser implements user(){
    userId;
    name;

    List<BookCopy> bookList;

    borrowBook()

    getBorrowedBooks();

    notify();

    // getter methods for getting Id, name
}


public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}
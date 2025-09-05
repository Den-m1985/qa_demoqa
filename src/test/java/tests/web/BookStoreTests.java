package tests.web;

import data.pages.BookStorePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import tests.TestBase;

import java.util.List;
import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;

@Owner("DenisQA")
@Epic(value = "Book Store Application")
@Feature(value = "Book Store Page")
@Story(value = "Book Store Search")
@Tag("ui")
class BookStoreTests extends TestBase {
    private final BookStorePage bookStorePage = new BookStorePage();

    @Severity(NORMAL)
    @DisplayName("Should find one existing book by title: ")
    @ValueSource(strings = {"Git Pocket Guide", "Learning JavaScript Design Patterns"})
    @ParameterizedTest(name = "{0}")
    void shouldFindExistingBookByTitle(String bookName) {
        step("Open book store", bookStorePage::openPage);
        step("Search for book by concrete title", () -> bookStorePage.search(bookName));
        step("Find matching book", () -> bookStorePage.checkFoundBook(bookName));
    }

    static Stream<Arguments> shouldFindManyExistingBooksByTitle() {
        return Stream.of(Arguments.of(List.of(
                        "Learning JavaScript Design Patterns",
                        "Speaking JavaScript",
                        "Programming JavaScript Applications",
                        "Eloquent JavaScript, Second Edition"),
                "JavaScript"));
    }

    @Severity(CRITICAL)
    @DisplayName("Should find many existing books by keyword in title: ")
    @MethodSource()
    @ParameterizedTest(name = "{1}")
    void shouldFindManyExistingBooksByTitle(List<String> bookNames, String keyword) {
        step("Open book store", bookStorePage::openPage);
        step("Search for books by keyword in title", () -> bookStorePage.search(keyword));
        step("Find all matching books", () -> bookStorePage.checkFoundBooks(bookNames));
    }

    @Severity(MINOR)
    @DisplayName("Should get message when no matching book found")
    @CsvSource({"abc123, No rows found"})
    @ParameterizedTest()
    void noMatchingBooksTest(String bookName, String message) {
        step("Open book store", bookStorePage::openPage);
        step("Search for non-existing book", () -> bookStorePage.search(bookName));
        step("See message when no matching book found", () -> bookStorePage.checkNoDataMessage(message));
    }
}


import maze2D_logic.ASCII_MazeCell_2D;
import maze2D_logic.MazeCell;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test_MazeCell {
    @Test
    public void test_MazeCell() {
        MazeCell maze_cell_01, maze_cell_02, maze_cell_03, maze_cell_04;
        String str[], expected[];

        maze_cell_01 = new MazeCell(0, 0);
        maze_cell_02 = new MazeCell(0, 1);
        maze_cell_03 = new MazeCell(1, 0);
        maze_cell_04 = new MazeCell(1, 1);

        // Test expected fresh created cell ----------------------------------------------------------------
        str = ASCII_MazeCell_2D.get_2D_ASCII_ART(maze_cell_01);
        expected = new String[3];
        expected[0] = "###";
        expected[1] = "###";
        expected[2] = "###";

        assertEquals(expected[0], str[0]);
        assertEquals(expected[1], str[1]);
        assertEquals(expected[2], str[2]);

        // Test cells after the creation of a passage to WEST -------------------------------------------
        maze_cell_01.set_bidirectional_passage_to(maze_cell_02);

        str = ASCII_MazeCell_2D.get_2D_ASCII_ART(maze_cell_01);
        expected[0] = "###";
        expected[1] = "#  ";
        expected[2] = "###";
        assertEquals(expected[0], str[0]);
        assertEquals(expected[1], str[1]);
        assertEquals(expected[2], str[2]);
        str = ASCII_MazeCell_2D.get_2D_ASCII_ART(maze_cell_02);
        expected[0] = "###";
        expected[1] = "  #";
        expected[2] = "###";
        assertEquals(expected[0], str[0]);
        assertEquals(expected[1], str[1]);
        assertEquals(expected[2], str[2]);

        // Test cells after the creation of a passage to SOUTH -------------------------------------------
        maze_cell_02.set_bidirectional_passage_to(maze_cell_04);

        str = ASCII_MazeCell_2D.get_2D_ASCII_ART(maze_cell_02);
        expected[0] = "###";
        expected[1] = "  #";
        expected[2] = "# #";
        assertEquals(expected[0], str[0]);
        assertEquals(expected[1], str[1]);
        assertEquals(expected[2], str[2]);
        str = ASCII_MazeCell_2D.get_2D_ASCII_ART(maze_cell_04);
        expected[0] = "# #";
        expected[1] = "# #";
        expected[2] = "###";
        assertEquals(expected[0], str[0]);
        assertEquals(expected[1], str[1]);
        assertEquals(expected[2], str[2]);
    }

    @Test
    public void test_count_crossroads() {
        MazeCell cell = new MazeCell(1, 1);
        MazeCell[] neighbours = new MazeCell[4];

        neighbours[0] = new MazeCell(1, 0);
        neighbours[1] = new MazeCell(1, 2);
        neighbours[2] = new MazeCell(0, 1);
        neighbours[3] = new MazeCell(2, 1);

        assertEquals(0, cell.count_crossroads());

        cell.set_bidirectional_passage_to(neighbours[0]);
        assertEquals(0, cell.count_crossroads());

        cell.set_bidirectional_passage_to(neighbours[1]);
        assertEquals(0, cell.count_crossroads());

        cell.set_bidirectional_passage_to(neighbours[2]);
        assertEquals(1, cell.count_crossroads());

        cell.set_bidirectional_passage_to(neighbours[3]);
        assertEquals(2, cell.count_crossroads());
    }
}

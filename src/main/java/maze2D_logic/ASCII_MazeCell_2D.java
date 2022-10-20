package maze2D_logic;

/*
      N
    ******
  E ****** W
    ******
      S

  This ASCII-art is not to scale, it has just the purpose to illustrate
  the orientation of the cardinal points to better understand how
  North, West, South, and East are collocated in the array
 */
public class ASCII_MazeCell_2D {
    private static final char WALL = '#', AIR = ' ';

    public static String[] get_2D_ASCII_ART(MazeCell cell) {
        final int dim = 3;
        String[] ascii = new String[dim];

        for(int i = 0; i < dim; ++i) {
            ascii[i] = "" + WALL + WALL + WALL;
        }

        if(cell.has_passage_to(Directions.DIRECTION.NORTH)) {
            ascii[0] = "" + ascii[0].charAt(0) + AIR + ascii[0].charAt(dim-1);
            ascii[1] = "" + ascii[1].charAt(0) + AIR + ascii[1].charAt(dim-1);
        }
        if(cell.has_passage_to(Directions.DIRECTION.WEST)) {
            ascii[1] = "" + ascii[1].charAt(0) + AIR + AIR;
        }
        if(cell.has_passage_to(Directions.DIRECTION.SOUTH)) {
            ascii[1] = "" + ascii[1].charAt(0) + AIR + ascii[1].charAt(dim-1);
            ascii[2] = "" + ascii[2].charAt(0) + AIR + ascii[2].charAt(dim-1);
        }
        if(cell.has_passage_to(Directions.DIRECTION.EAST)) {
            ascii[1] = "" + AIR + AIR + ascii[1].charAt(dim-1);
        }

        return ascii;
    }
}

/*

###
###

######   ##################
######                  ###
######                  ###
######   ############   ###
######      #########   ###
######      #########   ###
#########   #########   ###
########    #########   ###
###         ###         ###
###         ###         ###
###   #########   #########
###   #########   #########

#########################
#########################
#####               #####
#####               #####
#####     #####     #####
#####     #####     #####
#####     ###############
#####     ###############

###
###
###

###########################
###########################
#### ######################
#### ######################
#    ######################
# ## ######################
# ## ######################
#    ######################
# #########################

 */
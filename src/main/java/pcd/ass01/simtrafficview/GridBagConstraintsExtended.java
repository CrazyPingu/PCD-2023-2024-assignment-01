package pcd.ass01.simtrafficview;

import java.awt.*;

/**
 * Class to extend the GridBagConstraints class adding more constructors
 */
public class GridBagConstraintsExtended extends GridBagConstraints {

    GridBagConstraintsExtended(int gridx, int gridy) {
        super();
        this.insets = new Insets(5, 5, 10, 5);
        this.gridx = gridx;
        this.gridy = gridy;
    }

    GridBagConstraintsExtended(int gridx, int gridy, Insets insets) {
        this(gridx, gridy);
        this.insets = insets;
    }

    GridBagConstraintsExtended(int gridx, int gridy, int anchor, int gridwidth) {
        this(gridx, gridy);
        this.anchor = anchor;
        this.gridwidth = gridwidth;
    }

    public GridBagConstraintsExtended(int gridx, int gridy, int west, Insets insets) {
        this(gridx, gridy, insets);
        this.anchor = west;
    }
}

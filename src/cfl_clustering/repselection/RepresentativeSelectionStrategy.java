package cfl_clustering.repselection;

import data_objects.Cluster;
import hac.experiment.custom.CustomDissimilarityMeasure;
import data_objects.TestCase;

public interface RepresentativeSelectionStrategy {
	public TestCase selectRepresentative(Cluster c1, CustomDissimilarityMeasure dis);
}

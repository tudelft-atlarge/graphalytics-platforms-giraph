/*
 * Copyright 2015 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.tudelft.graphalytics.giraph.algorithms.cd;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.apache.giraph.conf.ImmutableClassesGiraphConfiguration;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

import static nl.tudelft.graphalytics.giraph.algorithms.cd.CommunityDetectionConfiguration.*;

/**
 * Specialisation of {@link CommonCommunityDetectionComputation} for undirected graphs.
 *
 * @author Tim Hegeman
 */
public class UndirectedCommunityDetectionComputation extends CommonCommunityDetectionComputation<NullWritable> {

	@Override
	protected void doInitialisationStep(Vertex<LongWritable, LongWritable, NullWritable> vertex,
			Iterable<LongWritable> messages) {
		// Initialize the label of a vertex to its own id
		vertex.getValue().set(vertex.getId().get());
	}

	@Override
	protected int getNumberOfInitialisationSteps() {
		return 1;
	}

	@Override
	protected void propagateLabel(Vertex<LongWritable, LongWritable, NullWritable> vertex) {
		// Send the label of the vertex to all neighbours
		sendMessageToAllEdges(vertex, vertex.getValue());
	}

}
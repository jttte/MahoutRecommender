package com.recommand.RecommanderApp;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.hdfs.server.datanode.DataStorage;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class ElvaluationRecommander {

	public static void main(String[] args) throws IOException, TasteException {
		DataModel model = new FileDataModel(new File("data/R3_train.txt"));
		//RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
		RecommenderBuilder builder = new MyUserBasedRecommenderBuilder();
		double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
		System.out.println(result);

	}

}

class MyUserBasedRecommenderBuilder implements RecommenderBuilder {

	public Recommender buildRecommender(DataModel dataModel)
			throws TasteException {
		UserSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
		return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
	}

}

class MyItemBasedRecommenderBuilder implements RecommenderBuilder {

	public Recommender buildRecommender(DataModel dataModel)
			throws TasteException {
		ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
		return new GenericItemBasedRecommender(dataModel, similarity);
	}

}

/*class MySlopeOneRecommenderBuilder implements RecommenderBuilder {

	public Recommender buildRecommender(DataModel dataModel)
			throws TasteException {
		CachingRecommender cachingRecommender = new CachingRecommender(new SlopeOneRecommender(dataModel));
		return cachingRecommender;
	}

}*/
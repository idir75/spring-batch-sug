/**
 *
 */
package fr.sug.springbatch.example.listener;

import fr.sug.springbatch.example.beans.Recipe;
import org.apache.log4j.Logger;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bazoud
 *
 */
public class StepRecipeExecutionListener extends ItemListenerSupport<Recipe, Recipe> {
    // logger
    private static final Logger LOG = Logger.getLogger(StepRecipeExecutionListener.class);
    private FlatFileItemWriter<Recipe> recipeExcludeWriter;

    @Override
    public void afterProcess(Recipe item, Recipe result) {
        if (result == null) {
            LOG.info("Filtering " + item.getName());
            try {
                List<Recipe> excludes = new ArrayList<Recipe>();
                excludes.add(item);
                recipeExcludeWriter.write(excludes);
            } catch (Exception e) {
                LOG.error("Can not write exludes file", e);
            }
        }
    }

    @Required
    public void setRecipeExcludeWriter(FlatFileItemWriter<Recipe> recipeExcludeWriter) {
        this.recipeExcludeWriter = recipeExcludeWriter;
    }
}

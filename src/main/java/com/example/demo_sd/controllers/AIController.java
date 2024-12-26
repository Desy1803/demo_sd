package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.AiRequest;
import com.example.demo_sd.dto.ArticleDtoParser;
import com.example.demo_sd.entities.ArticleEntity;
import com.example.demo_sd.prompts.Prompts;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.*;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo_sd.utils.ArticleParser.parseArticle;
import static com.example.demo_sd.utils.ArticleParser.removeAsterisks;

@RestController
@RequestMapping("/api")
@CrossOrigin
class AIController {

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/ai")
	public ArticleEntity aiGoogle(@RequestBody AiRequest data) throws IOException {

		String falseString = "false";
		GenerationConfig generationConfig = GenerationConfig.newBuilder()
				.setMaxOutputTokens(8192)
				.setTemperature(1F)
				.setTopP(0.95F)
				.build();

		List<SafetySetting> safetySettings = Arrays.asList(
				SafetySetting.newBuilder()
						.setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
						.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
						.build(),
				SafetySetting.newBuilder()
						.setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
						.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
						.build(),
				SafetySetting.newBuilder()
						.setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
						.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
						.build(),
				SafetySetting.newBuilder()
						.setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
						.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
						.build()
		);

		Prompts prompts = new Prompts();
		prompts.setCompany(data.company());
		prompts.setCategory(data.category());
		String addDate="";
		if(data.date()!=null)
			addDate=" for the fiscal year "+ data.date().getYear();
		Content systemInstruction;
		var content = ContentMaker.fromMultiModalData("Write a comprehensive article about financial analysis in the " + data.category() + " of " + data.company() +addDate+". The article should be approximately 1500 words, adopt a professional and analytical tone, and target a business-oriented audience. Please ensure that the data used for the analysis is from reliable and up-to-date sources. Cite your sources appropriately.\n" +
				" \n" +
				"The article must follow the specified structure:\n" +
				"\n" +
				"**Title**: Provide a clear and concise title for the article. This should represent the core topic or theme.\n" +
				"**I. Introduction**\n" +
				"* Briefly introduce " + data.company() + " and its position in the market.\n" +
				"* State the purpose of the article, which is to analyze " + data.company() + "'s " + data.category() + "\n" +
				" \n" +
				"**II. Data Analysis**\n" +
				" \n" +
				"**III. Future Outlook**\n" +
				" \n" +
				"**IV. Conclusion**\n" +
				" \n" +
				"Here's a few-shot example to guide your writing style:\n" +
				" \n" +
				"**Example Title: A Deep Dive into Microsoft's Profitability**\n" +
				" \n" +
				"**(Introduction)** Microsoft, a global technology giant, has consistently demonstrated strong financial performance. This article aims to delve into a detailed analysis of Microsoft's profitability and margins, providing insights into the company's financial health and future prospects...\n" +
				" \n" +
				"**(Data Analysis)** Over the past five years, Microsoft's gross profit margin has remained consistently above 60%, indicating the company's ability to effectively manage its cost of goods sold...\n" +
				" \n" +
				"**(Future Outlook)** The increasing demand for cloud services and digital transformation solutions presents a significant opportunity for Microsoft to further enhance its profitability. However, the company faces challenges such as increasing competition and evolving regulatory landscapes...\n" +
				" \n" +
				"**(Conclusion)** Microsoft's robust profitability and margins reflect its strong market position, innovative product portfolio, and effective business strategies. While challenges exist, the company is well-positioned for continued financial success...\n" +
				" \n" +
				"Your generated article should follow a similar structure and tone as the example provided. Please ensure that all data and analysis are supported by credible sources.");

		prompts.setPrompt(content);
		if (data.getSourcesFromGoogle().equals(falseString)) {
			try (VertexAI vertexAi = new VertexAI("theta-signal-433115-c2", "us-central1")) {
				if (data.dataOfCompany() == null) {
					systemInstruction = ContentMaker.fromMultiModalData(prompts.getSystemIstruction());
				} else {
					systemInstruction = ContentMaker.fromMultiModalData(prompts.getSystemIstruction() + "These are the information of the company: \n" + data.dataOfCompany());
				}
				System.out.println("Sys " + systemInstruction + "\n");
				System.out.println("Promp " + prompts.getPrompt() + "\n");
				GenerativeModel model = new GenerativeModel.Builder()
						.setModelName("gemini-1.5-flash-002")
						.setVertexAi(vertexAi)
						.setGenerationConfig(generationConfig)
						.setSafetySettings(safetySettings)
						.setSystemInstruction(systemInstruction)
						.build();
				ChatSession chatSession = model.startChat();
				GenerateContentResponse response = chatSession.sendMessage(prompts.getPrompt());
				String output = ResponseHandler.getText(response);
				System.out.println(output);

				return transformOutput(output, data.company(), data.category(), data.category());

			}
		} else {
			if (data.dataOfCompany() == null) {
				return byGoogle(data);
			}
			return null;
		}

	}

	private static ArticleEntity byGoogle(AiRequest data) throws IOException {
		try (VertexAI vertexAi = new VertexAI("theta-signal-433115-c2", "us-central1"); ) {
			List<Tool> tools = new ArrayList<>();
			tools.add(
					Tool.newBuilder()
							.setGoogleSearchRetrieval(
									GoogleSearchRetrieval.newBuilder())
							.build());
			GenerationConfig generationConfig =
					GenerationConfig.newBuilder()
							.setMaxOutputTokens(8192)
							.setTemperature(1F)
							.setTopP(0.95F)
							.build();
			List<SafetySetting> safetySettings = Arrays.asList(
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
							.build(),
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
							.build(),
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
							.build(),
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_NONE)
							.build()
			);

			Prompts prompts = new Prompts();
			prompts.setCompany(data.company());
			prompts.setCategory(data.category());

			var textsi_1 = prompts.getSystemIstruction();
			var systemInstruction = ContentMaker.fromMultiModalData(textsi_1);
			GenerativeModel model =
					new GenerativeModel.Builder()
							.setModelName("gemini-1.5-flash-002")
							.setVertexAi(vertexAi)
							.setGenerationConfig(generationConfig)
							.setSafetySettings(safetySettings)
							.setTools(tools)
							.setSystemInstruction(systemInstruction)
							.build();

			var text6_1 = "***Title: Financial Analysis of"+data.company()+"'s "+data.category()+"***\n" +
					"\n" +
					"**I. Introduction**\n" +
					"\n" +
					"* Briefly introduce "+data.company()+ "and its position in the market.  Provide context such as market share, key competitors, and recent performance.\n" +
					"* Clearly state the purpose of the article, which is to analyze "+data.company()+"'s "+data.category()+". Define the scope of the analysis, specifying the aspects of "+data.category()+" that will be covered.\n" +
					"\n" +
					"**II. Data Analysis**\n" +
					"\n" +
					"* Analyze the key financial data related to "+data.company()+"'s "+data.category()+". This could include revenue, profitability, market share, growth rates, and other relevant metrics.\n" +
					"* Use data visualizations such as charts and graphs to present the data clearly and effectively.\n" +
					"* Explain the trends and patterns observed in the data.  Provide insights into the factors driving these trends.\n" +
					"* Compare "+data.company()+"'s performance in "+data.category()+" to its competitors. Benchmark against industry averages.\n" +
					"* Identify the strengths and weaknesses of "+data.company()+"'s "+data.category()+" based on the data analysis.\n" +
					"\n" +
					"**III. Future Outlook**\n" +
					"\n" +
					"* Discuss the potential future trends and challenges for "+data.company()+"'s "+data.category()+"\n" +
					"* Consider factors such as market conditions, technological advancements, regulatory changes, and competitive landscape.\n" +
					"* Provide projections for "+data.company()+"'s future performance in "+data.category()+" based on the analysis.\n" +
					"* Offer recommendations for "+data.company()+"to improve its performance in "+data.category()+".\n" +
					"\n" +
					"**IV. Conclusion**\n" +
					"\n" +
					"* Summarize the key findings of the financial analysis.\n" +
					"* Reiterate the strengths and weaknesses of "+data.company()+"'s "+data.category() + "\n"+
					"* Offer a concise and insightful concluding statement about the overall financial health and prospects of "+data.company()+"'s "+data.category() +"\n" +
					"\n" +
					"Ensure all data used is from reliable and reputable sources. Cite these sources appropriately using footnotes.  The article should be well-written, insightful, and provide a comprehensive understanding of "+data.company()+"'s financial performance in "+data.category()+".\n" +
					"\"\"\"" ;
			GenerateContentResponse response;
			ChatSession chatSession = model.startChat();
			response = chatSession.sendMessage(ContentMaker.fromMultiModalData(text6_1));
			String output = ResponseHandler.getText(response);
			System.out.println(output);
			return transformOutput(output, data.company(), data.category(), data.category());
		}
	}

	private static ArticleEntity transformOutput(String output, String company, String date, String category) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		LocalDateTime now = LocalDateTime.now();
		ArticleDtoParser article = parseArticle(output);
		article.setTitle(removeAsterisks(article.getTitle()));
		return new ArticleEntity(null, article.getTitle(), article.getDescription(), company, username, null, date, true, category, true, now, now);
	}
}

package githubissuedumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.IssueService;

public class Main {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("output github closed issues.");
			System.out.println("Usage: GithubIssueDumper user-name repository-name output-filename");
			System.out.println("Example: GithubIssueDumper junit-team junit junit-issues.csv");
			System.exit(1);
		}
		String userName = args[0];
		String repositoryName = args[1];
		String outputFilename = args[2];
		GitHubClient client = new GitHubClient();
		IssueService issueService = new IssueService(client);
		Map<String,String> issueFileter = new HashMap<String,String>();
		issueFileter.put("state", "all");
		PageIterator<Issue> pageItr = issueService.pageIssues(userName, repositoryName, issueFileter);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // ISO 8601
		File file = new File(outputFilename);
		try(FileWriter writer = new FileWriter(file)) {
			StringBuilder sb = new StringBuilder();
			while(pageItr.hasNext())
			{
				System.out.println("progress "+pageItr.getNextPage()+"/"+pageItr.getLastPage());
				for(Issue issue : pageItr.next())
				{
					sb.setLength(0);
					sb.append(issue.getNumber());
					sb.append(", ");
					sb.append(issue.getState());
					sb.append(",");
					sb.append(issue.getUser().getLogin());
					sb.append(",");
					sb.append(simpleDateFormat.format(issue.getCreatedAt()));
					sb.append(", ");
					if (issue.getClosedAt() != null) {
						sb.append(simpleDateFormat.format(issue.getClosedAt()));
					}
					sb.append(",\"");
					sb.append(issue.getLabels().toString());
					sb.append("\",\"");
					sb.append(issue.getTitle().replaceAll("\"", "\"\"")); // RFC 4180
					sb.append("\"\n");
					writer.write(sb.toString());
				}
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("end");
	}
}

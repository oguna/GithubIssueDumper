package githubissuedumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.IssueService;

public class Main {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Output GitHub issues.");
			System.out.println("Usage: GitHubIssueDumper user repository output [your-username:your-password]");
			System.out.println("Example: GitHubIssueDumper junit-team junit junit-issues.csv my-name:my-pass");
			System.exit(1);
		}
		String userName = args[0];
		String repositoryName = args[1];
		String outputFilename = args[2];
		GitHubClient client = new GitHubClient();
		if (args.length >= 4) {
			String[] credentials = args[3].split(":");
			client.setCredentials(credentials[0], credentials[1]);
		}
		IssueService issueService = new IssueService(client);
		Map<String, String> issueFilter = Collections.singletonMap("state", "all");
		PageIterator<Issue> pageItr = issueService.pageIssues(userName, repositoryName, issueFilter);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // ISO 8601
		File file = new File(outputFilename);
		try (FileWriter writer = new FileWriter(file)) {
			StringBuilder sb = new StringBuilder();
			while (pageItr.hasNext()) {
				System.out.println("progress " + pageItr.getNextPage() + "/" + pageItr.getLastPage());
				for (Issue issue : pageItr.next()) {
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

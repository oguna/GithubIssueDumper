# Github Issue Dumper

Githubで管理されたリポジトリのIssueをダウンロードします。
これはリポジトリマイニングのために作られたので、
簡単な項目しかダウンロードしません。
完全なバックアップ目的ではありません。

各Issueについて以下の項目をダウンロードします。

- ID
- State
- User ID
- Created At
- Closed At
- Labels
- Title

ダウンロードする際は、GithubAPIのレート制限に注意してください。

適当な場所に下記コードを追加し、userとpasswordを自分のアカウントのに設定すると、制限が緩和されます。

```java
client.setCredentials(user, password);
```

Public Domain (CC0) のもとで配布されます。


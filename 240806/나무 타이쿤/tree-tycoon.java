import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//구현
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
        int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] ddx = {-1, -1, 1, 1}; //대각선
        int[] ddy = {-1, 1, -1, 1};
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); //배열길이
        int m = Integer.parseInt(st.nextToken()); //년수
        int[][] graph = new int[n][n];
        Stack<int[]> now = new Stack<>(); //현재 영양제 좌표!!
        now.add(new int[]{n - 1, 0}); //초기값 넣기
        now.add(new int[]{n - 1, 1});
        now.add(new int[]{n - 2, 0});
        now.add(new int[]{n - 2, 1});

        for (int i = 0; i < n; i++) { //그래프 채우기
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //m년 이후 남아있는 리브로수의 총 높이의 합
        for (int a = 0; a < m; a++) { //년도 진행
            List<int[]> next = new ArrayList<>(); //이동 후 영양제 좌표!!
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken()) - 1; //방향
            int p = Integer.parseInt(st.nextToken()); //이동칸수
            while (!now.isEmpty()) { // 모든 칸 이동
                int[] xy = now.pop();
                int nx = (xy[0] + dx[d] * p) % n; //d방향 p칸만큼 이동 - 무한맵
                int ny = (xy[1] + dy[d] * p) % n;
                if (nx < 0) {
                    nx = 5 + nx;
                }
                if (ny < 0) {
                    ny = 5 + ny;
                }
                graph[nx][ny]++; //영양제땅++
                next.add(new int[]{nx, ny}); //이동 후 땅에 푸시
            }
            //특수영양제 땅 중 대각선으로 인접한 높이 1 이상의 리브로수의 개수만큼 ++
            for (int i = 0; i < next.size(); i++){
                for (int k = 0; k < 4; k++) {
                    int wx = next.get(i)[0] + ddx[k];
                    int wy = next.get(i)[1] + ddy[k];
                    if (wx >= 0 && wx < n && wy >= 0 && wy < n && graph[wx][wy] >= 1) {
                        graph[next.get(i)[0]][next.get(i)[1]]++;
                    }
                }
            }
            //특수영양제땅 제외 높이 2이상이면 -2하고 특수영양제 뿌림 반복
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int flag = 0;
                    for (int[] xy : next) {
                        if (xy[0] == i && xy[1] == j) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 1) {
                        continue;
                    }
                    if (graph[i][j] >= 2) {
                        graph[i][j] -= 2;
                        now.add(new int[]{i, j});
                    }
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum+= graph[i][j];
            }
        }
        System.out.println(sum);
    }
}
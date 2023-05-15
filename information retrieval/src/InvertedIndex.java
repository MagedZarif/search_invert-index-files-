

import java.io.*;
import java.util.*;

//Maged Zarif Saleb (CS).   20210615   S7,S8
//Youssef Ashraf      (IT).   20210584   S7,S8
//Muhammed Bahaa    (IS).   20200434   S7,S8



public class InvertedIndex
{

    public static class Posting
    {
        public Posting next = null;
        public int docId;
        public int dtf = 1;    // document term frequency

        public Posting(int docId)
        {
            this.docId = docId;
        }
    }

    public static class DictEntry
    {
        public int doc_freq = 0;      // number of documents that contain the term

        public int term_freq = 0;    //number of times the term is mentioned in the collection
        public Posting pList = null;
    }

    public static void main(String[] args)
    {
        HashMap<String, DictEntry> index = new HashMap<>();
        String[] filenames = {"D:\\Faculty\\ir\\file1.txt", "D:\\Faculty\\ir\\file2.txt", "D:\\Faculty\\ir\\file3.txt", "D:\\Faculty\\ir\\file4.txt", "D:\\Faculty\\ir\\file5.txt", "D:\\Faculty\\ir\\file6.txt", "D:\\Faculty\\ir\\file7.txt", "D:\\Faculty\\ir\\file8.txt", "D:\\Faculty\\ir\\file9.txt", "D:\\Faculty\\ir\\file10.txt"};
        int docId = 0;

        // Read files and build index
        for (String filename : filenames)
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = br.readLine()) != null)
                {
                    String[] words = line.split("\\W+");
                    for (String word : words)
                    {
                        word = word.toLowerCase();
                        if (!index.containsKey(word))
                        {
                            index.put(word, new DictEntry());
                        }
                        DictEntry entry = index.get(word);
                        entry.term_freq++;
                        if (entry.pList == null || entry.pList.docId != docId) {
                            Posting posting = new Posting(docId);
                            posting.next = entry.pList;
                            entry.pList = posting;
                            entry.doc_freq++;
                        } else
                        {
                            entry.pList.dtf++;
                        }
                    }
                }
                br.close();
                docId++;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        // Search for a word
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a word to search: ");
        String query = input.nextLine().toLowerCase();
        if (index.containsKey(query))
        {
            DictEntry data = index.get(query);
            System.out.printf("(%s) there in (%d) documents:\n", query, data.doc_freq);
            Posting post = data.pList;
            while (post != null)
            {
                System.out.printf(" - file%d.txt and (number of iterations = %d)\n", post.docId + 1, post.dtf);
                post = post.next;
            }
        }

        else
        {
            System.out.printf("Sorry, (%s) is not in the files .\n", query);
        }
    }
}

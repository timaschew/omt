package marcelkarras.diploma.model.hofmanengl;

/*public class RhythmicSimilarity {

	private static final double k1 = 0.00000001;
	private static final double k2 = 0.00000001;

	/**
	 * Calculate the rhythmic similarity of two melodies.
	 * 
	 * @param F1
	 *            first frequency array
	 * @param F2
	 *            second frequency array
	 * @return double the similarity value between 0...1
	 */
	/*public static final double calculate(final double[] F1, final double[] F2) {
		// constraint: equal size chunks
		int L = (F1.length > F2.length) ? F2.length : F1.length;
		double[] T = new double[L];
		double[] I1 = new double[L];
		double[] I2 = new double[L];
		double T1 = 0, T2 = 0;
		double I3 = 0, I4 = 0;
		double sp = 0;
		L = L - 1;

		for (int l = 1; l <= L; l++) {
			T[l] = Math.log(F1[l] / F2[l]) / Math.log(2);
		}
		for (int l = 1; l <= L - 1; l++) {
			I1[l] = Math.log(F1[l + 1] / F1[l]) / Math.log(2);
			I2[l] = Math.log(F2[l + 1] / F2[l]) / Math.log(2);
		}

		for (int l = 1; l <= L; l++) {
			T1 = T1
					+ Math.pow(Math.exp((-k1 / Math.pow(L, 1))
							* Math.pow(T[l], 2)), 2);
		}
		T2 = Math.sqrt(T1 / L);
		if (L <= 1) {
			I4 = 1;
		} else {
			for (int l = 1; l <= L - 1; l++) {
				I3 = I3
						+ Math.pow(Math.exp((-k2 / Math.pow(L - 1, -2))
								* Math.pow((I1[l] - I2[l]), 2)), 2);
			}
			I4 = Math.sqrt(I3 / (L - 1));
		}
		sp = T2 * I4;
		return sp;
	}
} */



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;




public class CsimApplet extends JApplet implements ActionListener

{

private JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
private JLabel add1, note1, add2, note2, chain1, chain2, empty1, empty2, empty3, empty4, space1, space2, space3, space4, length1label, length2label, TempSim, DistribLabel, SimLabel;
private JTextField chain1i, chain2i, length1, length2, TempValue, DistribValue, Similarity;
private JButton v11, v12, v13, v14, v15, v16, v17, v18;
private JButton v21, v22, v23, v24, v25, v26, v27, v28;
private JButton clear1, clear2, clearlast1, clearlast2, clearall, process;


private String firstchain, secondchain;
private String[] onechain = new String[100];
private String[] twochain = new String[100];
private int i=0,j=0, k, n , al1=0, al2;
private double[] v1 = new double[100];
private double[] v2 = new double[100];
private double l1, l2, SumV1, SumV2, F3, v, s, z1, z2, diff, L, T1=0, M, S;
private double [] a1 = new double [100000];
private double [] a2 = new double [100000];
private double [] T = new double [100000];
private double [] o = new double [100000];
private double [] p = new double [100000];

public void init()

{

Container c = getContentPane();
c.setLayout(new BorderLayout());
p1 = new JPanel();
p2 = new JPanel();
p3 = new JPanel();
p3.setLayout(new GridLayout(4,3, 5 , 15));
p4 = new JPanel();
p5 = new JPanel();
p6 = new JPanel();
p7 = new JPanel();
p8 = new JPanel();
p9 = new JPanel();
p10 = new JPanel();
p11 = new JPanel();

c.add("North", p1);
c.add("South", p2);
c.add("Center",p3);

add1 = new JLabel("add");
v11 = new JButton("whole");
v11.addActionListener(this);
v12 = new JButton("half");
v12.addActionListener(this);
v13 = new JButton("quarter");
v13.addActionListener(this);
v14 = new JButton("eights");
v14.addActionListener(this);
v15 = new JButton("12ths");
v15.addActionListener(this);
v16 = new JButton("16ths");
v16.addActionListener(this);
v17 = new JButton("20ths");
v17.addActionListener(this);
v18 = new JButton("32nds");
v18.addActionListener(this);
note1 = new JLabel("note");

p1.add(add1);
p1.add(v11);
p1.add(v12);
p1.add(v13);
p1.add(v14);
p1.add(v15);
p1.add(v16);
p1.add(v17);
p1.add(v18);
p1.add(note1);

add2 = new JLabel("add");
v21 = new JButton("whole");
v21.addActionListener(this);
v22 = new JButton("half");
v22.addActionListener(this);
v23 = new JButton("quarter");
v23.addActionListener(this);
v24 = new JButton("eights");
v24.addActionListener(this);
v25 = new JButton("12ths");
v25.addActionListener(this);
v26 = new JButton("16ths");
v26.addActionListener(this);
v27 = new JButton("20ths");
v27.addActionListener(this);
v28 = new JButton("32nds");
v28.addActionListener(this);
note2 = new JLabel("note");

length1label = new JLabel(" Length of c1");
length1 = new JTextField(3);
length2label = new JLabel("Length of c2");
length2 = new JTextField(3);

p8.add(length1label);
p8.add(length1);
p8.add(length2label);
p8.add(length2);

TempSim = new JLabel("Similarity in Tempo         ");
TempValue = new JTextField(4);

p9.add(TempSim);
p9.add(TempValue);

DistribLabel = new JLabel("Similarity in Distribution");
DistribValue = new JTextField(4);
p10.add(DistribLabel);
p10.add(DistribValue);

SimLabel = new JLabel("Similarity                           ");
Similarity = new JTextField(4);
p11.add(SimLabel);
p11.add(Similarity);


p2.add(add2);
p2.add(v21);
p2.add(v22);
p2.add(v23);
p2.add(v24);
p2.add(v25);
p2.add(v26);
p2.add(v27);
p2.add(v28);
p2.add(note2);

empty1 = new JLabel();
chain1 = new JLabel("             First rhythm/C-chain");
empty2 = new JLabel();
space1 = new JLabel();
chain1i = new JTextField(40);
clear1 = new JButton("clear");
clear1.addActionListener(this);
clearlast1 = new JButton("clear last");
clearlast1.addActionListener(this);
space3 = new JLabel();
chain2i = new JTextField(40);
clear2 = new JButton("clear");
clear2.addActionListener(this);
clearlast2 = new JButton("clear last");
clearlast2.addActionListener(this);
empty3 = new JLabel();
chain2 = new JLabel("          Second rhythm/C-chain");
empty4 = new JLabel();

p4.add("East", clear1);
p4.add("West", clearlast1);
p5.add("East", clear2);
p5.add("West", clearlast2);

process = new JButton("Process Data");
process.addActionListener(this);


p6.add(process);

clearall = new JButton("Clear all");
clearall.addActionListener(this);


p7.add(clearall);

p3.add(p8);
p3.add(chain1);
p3.add(p6);
p3.add(p9);
p3.add(chain1i);
p3.add(p4);
p3.add(p10);
p3.add(chain2i);
p3.add(p5);
p3.add(p11);
p3.add(chain2);
p3.add(p7);

onechain[0] = " ";
twochain[0] = " ";


}

 public void actionPerformed(ActionEvent press)

{

if (press.getSource() == clearlast1)

{
onechain[i] = onechain[i-1];
chain1i.setText(onechain[i]);
i=i-1;
}

if (press.getSource() == clearlast2)

{
twochain[j] = twochain[j-1];
chain2i.setText(twochain[j]);
j=j-1;

}


if (press.getSource() == clear1)

{
onechain[0] = " ";
chain1i.setText(onechain[0]);
i=0;
}

if (press.getSource() == clear2)

{
twochain[0] = " ";
chain2i.setText(onechain[0]);
j=0;
}

if (press.getSource() == clearall)

{
onechain[0] = " ";
chain1i.setText(onechain[0]);
i=0;
twochain[0] = " ";
chain2i.setText(onechain[0]);
j=0;
length1.setText(" ");
length2.setText(" ");
TempValue.setText(" ");
DistribValue.setText(" ");
Similarity.setText(" ");
SumV1=0;
SumV2=0;
T1 = 0;
al1 = 0;
al2 = 0;
L = 0;


}

if (press.getSource() == v11)


{
v1[i] = 1;
i++;
onechain[i] = onechain[i-1] + " 1";
chain1i.setText(onechain[i]);
}


if (press.getSource() == v12)


{
v1[i] = 0.5;
i++;
onechain[i] = onechain[i-1] + " 1/2";
chain1i.setText(onechain[i]);
}


if (press.getSource() == v13)


{
v1[i] = 0.25;
i++;
onechain[i] = onechain[i-1] + " 1/4";
chain1i.setText(onechain[i]);
}


if (press.getSource() == v14)


{
v1[i] = 0.125;
i++;
onechain[i] = onechain[i-1] + " 1/8";
chain1i.setText(onechain[i]);
}


if (press.getSource() == v15)


{
v1[i] = 0.08333;
i++;
onechain[i] = onechain[i-1] + " 1/12";
chain1i.setText(onechain[i]);
}


if (press.getSource() == v16)


{
v1[i] = 0.0625;
i++;
onechain[i] = onechain[i-1] + " 1/16";
chain1i.setText(onechain[i]);
}


if (press.getSource() == v17)


{
v1[i] = 0.05;
i++;
onechain[i] = onechain[i-1] + " 1/20";
chain1i.setText(onechain[i]);
}


if (press.getSource() == v18)


{
v1[i] = 0.03125;
i++;
onechain[i] = onechain[i-1] + " 1/32";
chain1i.setText(onechain[i]);
}

if (press.getSource() == v21)


{
v2[j] = 1;
j++;
twochain[j] = twochain[j-1] + " 1";
chain2i.setText(twochain[j]);
}


if (press.getSource() == v22)


{
v2[j] = 0.5;
j++;
twochain[j] = twochain[j-1] + " 1/2";
chain2i.setText(twochain[j]);
}


if (press.getSource() == v23)


{
v2[j] = 0.25;
j++;
twochain[j] = twochain[j-1] + " 1/4";
chain2i.setText(twochain[j]);
}


if (press.getSource() == v24)


{
v2[j] = 0.125;
j++;
twochain[j] = twochain[j-1] + " 1/8";
chain2i.setText(twochain[j]);
}


if (press.getSource() == v25)


{
v2[j] = 0.08333;
j++;
twochain[j] = twochain[j-1] + " 1/12";
chain2i.setText(twochain[j]);
}


if (press.getSource() == v26)


{
v2[j] = 0.0625;
j++;
twochain[j] = twochain[j-1] + " 1/16";
chain2i.setText(twochain[j]);
}


if (press.getSource() == v27)


{
v2[j] = 0.05;
j++;
twochain[j] = twochain[j-1] + " 1/20";
chain2i.setText(twochain[j]);
}


if (press.getSource() == v28)


{
v2[j] = 0.03125;
j++;
twochain[j] = twochain[j-1] + " 1/32";
chain2i.setText(twochain[j]);
}

if (press.getSource() == process)

{

for (int k = 0; k < i; k++)

{
SumV1 = SumV1 + v1[k];
}

for (int k = 0; k < j; k++)

{
SumV2 = SumV2 + v2[k];
}
v = 0.15 * Math.pow(Math.log(SumV1/SumV2),2);
F3 = Math.pow(2.17, -v);
F3 = Math.round(F3*10000);
F3 = F3/10000;
SumV1 = Math.round(SumV1*1000);
SumV1 = SumV1/1000;
SumV2 = Math.round(SumV2*1000);
SumV2 = SumV2/1000;


length1.setText(Double.toString(SumV1));
length2.setText(Double.toString(SumV2));
TempValue.setText(Double.toString(F3));

if (SumV2 > SumV1)

{
L = SumV2/SumV1;

for (int m = 0; m < i; m++)

{
v1[m] = L*v1[m];
}


}

if (SumV1 > SumV2)

{
L = SumV1/SumV2;

for (int m = 0; m < j; m++)

{
v2[m] = L*v2[m];
}

}


for (int m = 0; m < i; m++)

{
for (double k= 0.005; k < v1[m] ; k+=0.005)

{
al1++;
a1[al1] = v1[m];
}


}


for (int m = 0; m < j; m++)

{

for (double k= 0.005; k < v2[m] ; k+=0.005)

{
al2++;
a2[al2] = v2[m];
}


}


for (int m = 1; m < al1; m++)

{
T[m] = Math.log(a1[m]/a2[m])/Math.log(2);
}

for (int m = 1; m <al1; m++)

{

o[m] = Math.pow(T[m],2);
p[m] = Math.pow(2.17,(-12.8*o[m]));
T1 = T1 + Math.pow(p[m],2);
}

T1 = Math.sqrt(T1/al1);
T1 = Math.round(T1*1000+1);
T1 = T1/1000;
S = F3*T1;
S = Math.round(S*1000);
S = S/1000;
DistribValue.setText(Double.toString(T1));
Similarity.setText(Double.toString(S));


}
}

}
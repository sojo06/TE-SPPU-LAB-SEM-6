import streamlit as st

st.set_page_config(page_title="SecureBank", layout="wide")

# ---------------- CHATBOT LOGIC ----------------

def chatbot_response(user_input):

    user_input = user_input.lower()

    greetings = ["hello","hi","hey","good morning","good evening"]
    balance = ["balance","account balance"]
    loan = ["loan","borrow"]
    card = ["credit card","card"]
    lost_card = ["lost card","stolen card"]
    atm = ["atm"]
    transfer = ["transfer","neft","rtgs","imps"]
    digital = ["internet banking","mobile banking"]
    hours = ["hours","open","closing"]
    complaint = ["complaint","issue"]
    branch = ["branch","location"]
    city=["pune","bangalore","delhi"]

    if any(x in user_input for x in greetings):
        return """Hello! Welcome to SecureBank 🤖  

You can ask about:  
• Account balance  
• Loans  
• Credit cards  
• ATM services  
• Money transfer  
• Internet banking  
• Branch locations"""

    elif any(x in user_input for x in balance):
        return "Enter your 10-digit account number to check balance."

    elif any(x in user_input for x in loan):
        return "We offer Home, Car, and Personal loans starting from 8.5% interest."

    elif any(x in user_input for x in card):
        return "Credit card services include apply, limit increase, and activation."

    elif any(x in user_input for x in lost_card):
        return "Your card should be blocked immediately, type 'block card'."

    elif "block card" in user_input:
        return "Your card has been blocked successfully."

    elif any(x in user_input for x in atm):
        return "ATM services include locating ATM, withdrawal limits, and card issues."

    elif any(x in user_input for x in transfer):
        return "Money transfer options include NEFT, RTGS, IMPS, and UPI."

    elif any(x in user_input for x in digital):
        return "Use SecureBank mobile app or website for digital banking."

    elif any(x in user_input for x in complaint):
        return "Describe your issue and a support ticket will be created."

    elif any(x in user_input for x in branch):
        return "Main branch: Kothrud Pune 411038, Sub branch: Katraj Pune 411043."

    elif any(x in user_input for x in city):
        return "Enter your city name to find the nearest branch."

    elif any(x in user_input for x in hours):
        return "Branches operate Monday to Friday from 9 AM to 5 PM."

    elif user_input.isdigit() and len(user_input)==10:
        return "Your account balance is ₹45,230."

    else:
        return """Sorry I didn't understand.  

Try asking about:  
• Balance  
• Loans  
• Credit cards  
• ATM services  
• Internet banking"""
# ---------------- NAVBAR ----------------

st.markdown("""
<style>

.navbar{
background-color:#0e2a47;
padding:15px;
color:white;
font-size:24px;
font-weight:bold;
}

.chat-button{
position:fixed;
bottom:20px;
right:20px;
background-color:#0e2a47;
color:white;
border-radius:50%;
width:60px;
height:60px;
display:flex;
align-items:center;
justify-content:center;
font-size:28px;
cursor:pointer;
}

</style>

<div class="navbar">
🏦 SecureBank
</div>

""", unsafe_allow_html=True)

# ---------------- HERO SECTION ----------------

st.title("Welcome to SecureBank")

st.write(
"""
SecureBank provides secure digital banking services including
accounts, loans, credit cards and online banking.
"""
)

st.divider()

# ---------------- SERVICES ----------------

col1,col2,col3 = st.columns(3)

with col1:
    st.subheader("Accounts")
    st.write("""
• Savings Account  
• Current Account  
• Salary Account  
""")

with col2:
    st.subheader("Loans")
    st.write("""
• Home Loan  
• Car Loan  
• Personal Loan  
""")

with col3:
    st.subheader("Cards")
    st.write("""
• Credit Cards  
• Debit Cards  
• Rewards Cards  
""")

st.divider()

# ---------------- DIGITAL BANKING ----------------

st.header("Digital Banking")

col1,col2 = st.columns(2)

with col1:
    st.write("""
📱 Mobile Banking  
Transfer money, pay bills, check balance using our mobile app.
""")

with col2:
    st.write("""
💻 Internet Banking  
Access your account securely from anywhere.
""")

st.divider()

# ---------------- CONTACT ----------------

st.header("Contact Us")

st.write("📞 Customer Care: +91 9876543210")
st.write("📧 support@securebank.com")
st.write("📍 Pune, India")

st.divider()

# ---------------- CHATBOT ----------------

st.markdown("### 💬 SecureBot Assistant")

if "messages" not in st.session_state:
    st.session_state.messages=[]

for msg in st.session_state.messages:
    with st.chat_message(msg["role"]):
        st.write(msg["content"])

user_input=st.chat_input("Ask banking question...")

if user_input:

    st.session_state.messages.append({
        "role":"user",
        "content":user_input
    })

    response=chatbot_response(user_input)

    st.session_state.messages.append({
        "role":"assistant",
        "content":response
    })

    st.rerun()
